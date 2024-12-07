package kg.example.practice_application.payload.users;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    String username;
    String email;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    String profileImageUrl;
}