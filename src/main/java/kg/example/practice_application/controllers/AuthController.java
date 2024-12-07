package kg.example.practice_application.controllers;

import kg.example.practice_application.payload.users.UserLoginDto;
import kg.example.practice_application.payload.users.UserLoginResponseDto;
import kg.example.practice_application.payload.users.UserRegisterDto;
import kg.example.practice_application.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDto userRegisterDto) {
        return new ResponseEntity<>(authService.register(userRegisterDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok(authService.login(userLoginDto));
    }

    @PostMapping("/restore-password")
    public ResponseEntity<String> restorePassword(@RequestParam String email) {
        return ResponseEntity.ok(authService.restorePassword(email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestParam String token,
                                              @RequestParam String newPassword) {
        authService.resetPassword(token, newPassword);
        return ResponseEntity.noContent().build();
    }

}

