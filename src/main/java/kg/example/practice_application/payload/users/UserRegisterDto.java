package kg.example.practice_application.payload.users;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegisterDto {
    String username;
    String email;
    String password;
}