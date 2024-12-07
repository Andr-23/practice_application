package kg.example.practice_application.service.impl;

import kg.example.practice_application.entities.Post;
import kg.example.practice_application.entities.Image;
import kg.example.practice_application.entities.User;
import kg.example.practice_application.mapper.PostMapper;
import kg.example.practice_application.payload.post_favourites.ShortFavouritePostDto;
import kg.example.practice_application.payload.post_images.PostImageShortDto;
import kg.example.practice_application.payload.posts.PostCreationDto;
import kg.example.practice_application.payload.posts.PostDto;
import kg.example.practice_application.payload.posts.PostEditDto;
import kg.example.practice_application.repository.PostRepository;
import kg.example.practice_application.repository.UserRepository;
import kg.example.practice_application.service.FavouritePostService;
import kg.example.practice_application.service.ImageService;
import kg.example.practice_application.service.PostService;
import kg.example.practice_application.context.UserContextHolder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostServiceImpl implements PostService {

    ImageService imageService;
    FavouritePostService favouritePostService;
    UserRepository userRepository;
    PostRepository postRepository;
    PostMapper postMapper;

    @Override
    public Page<PostDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(postMapper::toPostDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDto> getCurrentUserPosts(Pageable pageable) {
        User user = userRepository.findById(UserContextHolder.getUserId())
                .orElseThrow();
        List<Post> posts = user.getPosts();

        List<PostDto> postDtos = posts.stream()
                .map(postMapper::toPostDto)
                .toList();

        return getPageFromList(postDtos, pageable);
    }

    @Override
    public Page<PostDto> getPostsByUserId(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow();

        List<Post> posts = user.getPosts();

        List<PostDto> postDtos = posts.stream()
                .map(postMapper::toPostDto)
                .toList();

        return getPageFromList(postDtos, pageable);
    }

    private Page<PostDto> getPageFromList(List<PostDto> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        if (start > list.size()) {
            return Page.empty(pageable);
        }

        List<PostDto> pageContent = list.subList(start, end);
        return new PageImpl<>(pageContent, pageable, list.size());
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        return postMapper.toPostDto(post);
    }

    @Override
    public PostDto createPost(PostCreationDto postCreationDto) {
        Post post = new Post();
        post.setTitle(postCreationDto.getTitle());
        post.setContent(postCreationDto.getContent());

        User user = userRepository.findById(UserContextHolder.getUserId()).orElseThrow();
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        List<Image> images = postCreationDto.getImages().stream()
                .map(postImageDto -> {
                    Image image = imageService.createPostImage(postImageDto);
                    image.setPost(savedPost);
                    return image;
                })
                .toList();

        imageService.saveAll(images);

        savedPost.setImages(images);

        return postMapper.toPostDto(savedPost);
    }

    @Override
    public PostDto updatePost(Long id, PostEditDto postEditDto) {
        Post post = postRepository.findByIdAndUserId(id, UserContextHolder.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("You can't update not your posts!"));

        if (postEditDto.getTitle() != null) {
            post.setTitle(postEditDto.getTitle());
        }
        if (postEditDto.getContent() != null) {
            post.setContent(postEditDto.getContent());
        }

        post = postRepository.save(post);
        return postMapper.toPostDto(post);
    }

    @Override
    public void addPostImageToPost(Long postId, PostImageShortDto postCreationDto) {
        Post post = postRepository.findById(postId).orElseThrow();
        Image image = imageService.createPostImage(postCreationDto);
        post.addPostImage(image);
        postRepository.save(post);
        image.setPost(post);
        imageService.save(image);
    }

    @Override
    public void deletePostImageByItsId(Long postImageId) {
        imageService.deletePostImage(postImageId);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findByIdAndUserId(id, UserContextHolder.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("You cannot delete alien posts!"));

        postRepository.delete(post);
    }

    @Override
    public PostDto addToFavorites(Long postId) {
        ShortFavouritePostDto shortFavouritePostDto = favouritePostService.addToFavourites(postId);
        Post post = postRepository.findById(shortFavouritePostDto.getPostId())
                .orElseThrow();
        return postMapper.toPostDto(post);
    }

    @Override
    public PostDto removeFromFavorites(Long postId) {
        ShortFavouritePostDto shortFavouritePostDto = favouritePostService.removeFromFavourites(postId);
        Post post = postRepository.findById(shortFavouritePostDto.getPostId())
                .orElseThrow();
        return postMapper.toPostDto(post);
    }
}
