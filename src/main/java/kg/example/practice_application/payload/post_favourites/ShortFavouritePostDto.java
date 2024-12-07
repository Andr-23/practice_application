package kg.example.practice_application.payload.post_favourites;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShortFavouritePostDto {
    Long id;
    Long userId;
    String username;
    Long postId;
    String postTitle;
    LocalDateTime createdAt;
}