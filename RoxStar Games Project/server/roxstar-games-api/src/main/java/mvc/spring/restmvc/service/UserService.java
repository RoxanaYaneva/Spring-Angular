package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser(User user);
    User updateUser(User user);
    User deleteUser(Long id);
}
