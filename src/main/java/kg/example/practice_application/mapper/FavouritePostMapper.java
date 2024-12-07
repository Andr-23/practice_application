package kg.example.practice_application.mapper;

import kg.example.practice_application.payload.post_favourites.ShortFavouritePostDto;
import kg.example.practice_application.entities.FavouritePost;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FavouritePostMapper {
    ShortFavouritePostDto toShortDto(FavouritePost favouritePost);
}