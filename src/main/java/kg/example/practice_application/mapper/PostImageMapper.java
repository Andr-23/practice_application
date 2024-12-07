package kg.example.practice_application.mapper;

import kg.example.practice_application.payload.post_images.PostImageDto;
import kg.example.practice_application.payload.post_images.PostImageShortDto;
import kg.example.practice_application.entities.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostImageMapper {
    Image toEntity(PostImageShortDto postImageShortDto);
    PostImageDto toPostImageDto(Image image);
}