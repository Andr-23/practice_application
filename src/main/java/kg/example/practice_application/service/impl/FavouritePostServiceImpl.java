package kg.example.practice_application.service.impl;

import kg.example.practice_application.payload.post_favourites.ShortFavouritePostDto;
import kg.example.practice_application.entities.FavouritePost;
import kg.example.practice_application.entities.Post;
import kg.example.practice_application.entities.User;
import kg.example.practice_application.mapper.FavouritePostMapper;
import kg.example.practice_application.repository.FavouritePostRepository;
import kg.example.practice_application.repository.PostRepository;
import kg.example.practice_application.repository.UserRepository;
import kg.example.practice_application.service.FavouritePostService;
import kg.example.practice_application.context.UserContextHolder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavouritePostServiceImpl implements FavouritePostService {

    PostRepository postRepository;
    FavouritePostRepository favouritePostRepository;
    FavouritePostMapper favouritePostMapper;
    UserRepository userRepository;

    @Override
    public ShortFavouritePostDto addToFavourites(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        User user = userRepository.findById(UserContextHolder.getUserId())
                .orElseThrow();

        boolean alreadyFavourite = favouritePostRepository.existsByUserAndPost(user, post);
        if (alreadyFavourite) {
            throw new IllegalArgumentException("Post already in favourites");
        }

        FavouritePost favouritePost = new FavouritePost();
        favouritePost.setPost(post);
        favouritePost.setUser(user);
        favouritePost = favouritePostRepository.save(favouritePost);

        return favouritePostMapper.toShortDto(favouritePost);
    }

    @Override
    public ShortFavouritePostDto removeFromFavourites(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        User user = userRepository.findById(UserContextHolder.getUserId()).orElseThrow();

        FavouritePost favouritePost = favouritePostRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new IllegalArgumentException("Favourite post not found"));

        favouritePostRepository.delete(favouritePost);

        return favouritePostMapper.toShortDto(favouritePost);
    }
}
