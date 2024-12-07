package kg.example.practice_application.service.impl;

import kg.example.practice_application.payload.post_images.PostImageDto;
import kg.example.practice_application.payload.post_images.PostImageShortDto;
import kg.example.practice_application.entities.Image;
import kg.example.practice_application.entities.User;
import kg.example.practice_application.mapper.PostImageMapper;
import kg.example.practice_application.repository.PostImageRepository;
import kg.example.practice_application.repository.UserRepository;
import kg.example.practice_application.service.ImageService;
import kg.example.practice_application.context.UserContextHolder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageServiceImpl implements ImageService {

    PostImageRepository postImageRepository;

    PostImageMapper postImageMapper;

    UserRepository userRepository;

    @Override
    public Page<PostImageDto> getAllPostImages(Pageable pageable) {
        Page<Image> postImages = postImageRepository.findAll(pageable);
        return postImages.map(postImageMapper::toPostImageDto);
    }

    @Override
    public Page<PostImageDto> getAllPostImagesByPostId(Long postId, Pageable pageable) {
        Page<Image> postImages = postImageRepository.findAllByPostId(postId, pageable);
        return postImages.map(postImageMapper::toPostImageDto);
    }

    @Override
    public PostImageDto getById(Long id) {
        return postImageMapper.toPostImageDto(postImageRepository.findById(id).orElseThrow());
    }

    @Override
    public Image createPostImage(PostImageShortDto postImageShortDto) {
        Image image = postImageMapper.toEntity(postImageShortDto);
        return postImageRepository.save(image);
    }

    @Override
    public Image save(Image image) {
        return postImageRepository.save(image);
    }

    @Override
    public List<Image> saveAll(List<Image> list) {
        return postImageRepository.saveAll(list);
    }

    @Override
    public void deletePostImage(Long id) {
        Image image = postImageRepository.findById(id).orElseThrow();
        User user = userRepository.findById(UserContextHolder.getUserId()).orElseThrow();
        if (!image.getPost().getUser().equals(user)) {
            throw new RuntimeException("You can't delete image not your image");
        };
        postImageRepository.delete(image);
    }
}
