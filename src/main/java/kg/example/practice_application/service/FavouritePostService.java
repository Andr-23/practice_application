package kg.example.practice_application.service;

import kg.example.practice_application.payload.post_favourites.ShortFavouritePostDto;

public interface FavouritePostService {
    ShortFavouritePostDto addToFavourites(Long postId);
    ShortFavouritePostDto removeFromFavourites(Long postId);
}
