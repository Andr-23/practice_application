package kg.example.practice_application.controllers;

import kg.example.practice_application.payload.users.UserDto;
import kg.example.practice_application.payload.users.UserUpdateDto;
import kg.example.practice_application.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping("/self")
    public ResponseEntity<UserDto> getCurrentUser() {
        UserDto user = userService.getUser();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getUsers(Pageable pageable) {
        Page<UserDto> users = userService.getUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/self")
    public ResponseEntity<UserDto> updateCurrentUser(@RequestBody UserUpdateDto userUpdateDto) {
        UserDto updatedUser = userService.updateUser(userUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/self")
    public ResponseEntity<Void> deleteCurrentUser() {
        userService.selfDelete();
        return ResponseEntity.noContent().build();
    }

}
