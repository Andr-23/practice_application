package kg.example.practice_application.service;

import kg.example.practice_application.payload.users.UserDto;
import kg.example.practice_application.payload.users.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto getUser();
    UserDto getUserById(Long id);
    Page<UserDto> getUsers(Pageable pageable);
    UserDto updateUser(UserUpdateDto userUpdateDto);
    UserDto selfDelete();
}
