package kg.example.practice_application.service.impl;

import kg.example.practice_application.payload.users.UserDto;
import kg.example.practice_application.payload.users.UserUpdateDto;
import kg.example.practice_application.entities.User;
import kg.example.practice_application.mapper.UserMapper;
import kg.example.practice_application.repository.UserRepository;
import kg.example.practice_application.service.UserService;
import kg.example.practice_application.context.UserContextHolder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    @Override
    public UserDto getUser() {
        User user = userRepository.findById(UserContextHolder.getUserId())
                .orElseThrow();
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow();
        return userMapper.toUserDto(user);
    }

    @Override
    public Page<UserDto> getUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toUserDto);
    }

    @Override
    public UserDto updateUser(UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(UserContextHolder.getUserId())
                .orElseThrow();

        if (userUpdateDto.getUsername() != null) {
            user.setUsername(userUpdateDto.getUsername());
        }
        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getProfileImageUrl() != null) {
            user.setProfileImageUrl(userUpdateDto.getProfileImageUrl());
        }
        user = userRepository.save(user);

        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto selfDelete() {
        User user = userRepository.findById(UserContextHolder.getUserId())
                .orElseThrow();
        userRepository.delete(user);
        return userMapper.toUserDto(user);
    }
}
