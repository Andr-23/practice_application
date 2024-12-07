package kg.example.practice_application.payload.posts;

import kg.example.practice_application.payload.post_images.PostImageDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto {
    Long id;
    Long userId;
    String username;
    String title;
    String content;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    List<PostImageDto> images;
}