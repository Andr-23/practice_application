package kg.example.practice_application.mapper;

import kg.example.practice_application.payload.posts.PostDto;
import kg.example.practice_application.entities.Post;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    PostDto toPostDto(Post post);

    @AfterMapping
    default void linkImagesDto(@MappingTarget PostDto postDto) {
        if (postDto.getImages() != null) {
            postDto.getImages().forEach(image -> image.setPostId(postDto.getId()));
        }
    }
}