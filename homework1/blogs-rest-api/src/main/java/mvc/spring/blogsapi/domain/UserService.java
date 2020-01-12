package mvc.spring.blogsapi.domain;

import lombok.NonNull;
import mvc.spring.blogsapi.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    User getUserById(@NonNull String userId);
    User getUserByEmail(@NonNull String email);
    User createUser(User user);
    User updateUser(User user);
    User deleteUser(String userId);
}
