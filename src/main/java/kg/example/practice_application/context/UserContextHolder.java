package kg.example.practice_application.context;

import kg.example.practice_application.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContextHolder {

    public static Long getUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new IllegalArgumentException("Current session user not found");
        }
        return user.getId();
    }

}
