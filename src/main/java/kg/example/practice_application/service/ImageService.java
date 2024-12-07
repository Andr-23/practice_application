package kg.example.practice_application.service;

import kg.example.practice_application.payload.post_images.PostImageDto;
import kg.example.practice_application.payload.post_images.PostImageShortDto;
import kg.example.practice_application.entities.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ImageService {
    Page<PostImageDto> getAllPostImages(Pageable pageable);

    Page<PostImageDto> getAllPostImagesByPostId(Long postId, Pageable pageable);

    PostImageDto getById(Long id);

    Image createPostImage(PostImageShortDto postImageShortDto);

    Image save(Image image);

    List<Image> saveAll(List<Image> list);

    void deletePostImage(Long id);
}
