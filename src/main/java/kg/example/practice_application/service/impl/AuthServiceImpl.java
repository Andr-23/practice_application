package kg.example.practice_application.service.impl;

import kg.example.practice_application.payload.users.UserLoginDto;
import kg.example.practice_application.payload.users.UserLoginResponseDto;
import kg.example.practice_application.payload.users.UserRegisterDto;
import kg.example.practice_application.entities.User;
import kg.example.practice_application.repository.UserRepository;
import kg.example.practice_application.service.AuthService;
import kg.example.practice_application.service.EmailService;
import kg.example.practice_application.service.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

    AuthenticationManager authenticationManager;

    PasswordEncoder passwordEncoder;

    JwtService jwtService;

    UserRepository userRepository;

    EmailService emailService;

    @Override
    public String register(UserRegisterDto userRegisterDto) {

        if (!userRegisterDto.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        User existingUser = userRepository.findByEmail(userRegisterDto.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("Email already in use");
        }
        existingUser = userRepository.findByUsername(userRegisterDto.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already in use");
        }
        if (userRegisterDto.getPassword() == null || userRegisterDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Invalid password");
        }
        if (userRegisterDto.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }

        User user = new User();
        user.setEmail(userRegisterDto.getEmail());
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        user = userRepository.save(user);

        return String.format("User %s created", user.getUsername());
    }

    @Override
    public UserLoginResponseDto login(UserLoginDto userLoginDto) {
        User user = userRepository.findByUsername(userLoginDto.getUsername());
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(),
                userLoginDto.getPassword()));
        String jwtToken = jwtService.generateJwtToken(user);
        return new UserLoginResponseDto(jwtToken);
    }

    @Override
    public String restorePassword(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("User with the that email does not exist");
        }

        String token = jwtService.generateJwtToken(user);

        String subject = "Password Reset Request";
        String body = String.format(
                "Hello %s, you are trying to reset your password, your token for password reset: %s, if you're not tried reset password, just ignore the message", user.getUsername(), token);

        emailService.sendEmail(user.getEmail(), subject, body);

        return "Password reset token sent to your email.";
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new IllegalArgumentException("Invalid jwt token");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
