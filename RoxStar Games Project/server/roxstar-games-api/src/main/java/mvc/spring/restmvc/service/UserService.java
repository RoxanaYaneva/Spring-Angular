package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.User;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(String id);
    Optional<User> getUserByEmail(String email);
    User createUser(@Valid User user);
    User updateUser(User user);
    User deleteUser(String id);
}
