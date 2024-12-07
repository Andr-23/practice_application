package kg.example.practice_application.mapper;

import kg.example.practice_application.payload.users.UserDto;
import kg.example.practice_application.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
}