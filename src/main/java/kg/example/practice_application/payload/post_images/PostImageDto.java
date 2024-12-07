package kg.example.practice_application.payload.post_images;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostImageDto {
    Long id;
    Long postId;
    String url;
    String description;
    LocalDateTime createdAt;
}