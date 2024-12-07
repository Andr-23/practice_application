package kg.example.practice_application.payload.posts;

import kg.example.practice_application.payload.post_images.PostImageShortDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostCreationDto {
    String title;
    String content;
    List<PostImageShortDto> images;
}