package kg.example.practice_application.service;

import kg.example.practice_application.payload.users.UserLoginDto;
import kg.example.practice_application.payload.users.UserLoginResponseDto;
import kg.example.practice_application.payload.users.UserRegisterDto;


public interface AuthService {
    String register(UserRegisterDto userRegisterDto);
    UserLoginResponseDto login(UserLoginDto userLoginDto);
    String restorePassword(String emal);
    void resetPassword(String token, String newPassword);
}
