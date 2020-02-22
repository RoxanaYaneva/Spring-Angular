package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.User;

import java.util.Set;

public interface UserService {
    Set<User> getAllUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    User createUser(User user);
    User updateUser(User user);
    User deleteUser(Long id);
}
