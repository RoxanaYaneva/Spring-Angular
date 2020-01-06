package mvc.spring.restmvc.service;

import lombok.NonNull;
import mvc.spring.restmvc.exception.UserAlreadyExistsException;
import mvc.spring.restmvc.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    User getUserById(@NonNull String userId);
    User getUserByEmail(@NonNull String email);
    User createUser(User user) throws UserAlreadyExistsException;
    User updateUser(User user);
    User deleteUser(String userId);
}
