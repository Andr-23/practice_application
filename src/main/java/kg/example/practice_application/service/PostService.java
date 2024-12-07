package kg.example.practice_application.service;

import kg.example.practice_application.payload.posts.PostCreationDto;
import kg.example.practice_application.payload.posts.PostDto;
import kg.example.practice_application.payload.posts.PostEditDto;
import kg.example.practice_application.payload.post_images.PostImageShortDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<PostDto> getAllPosts(Pageable pageable);
    Page<PostDto> getCurrentUserPosts(Pageable pageable);
    Page<PostDto> getPostsByUserId(Long id, Pageable pageable);
    PostDto getPostById(Long id);
    PostDto createPost(PostCreationDto postCreationDto);
    PostDto updatePost(Long id, PostEditDto postEditDto);
    void addPostImageToPost(Long postId, PostImageShortDto postCreationDto);
    void deletePostImageByItsId(Long postImageId);
    void deletePost(Long id);
    PostDto addToFavorites(Long postId);
    PostDto removeFromFavorites(Long postId);
}
