package mvc.spring.blogsapi.controller;

import mvc.spring.blogsapi.domain.UserService;
import mvc.spring.blogsapi.exception.InvalidEntityException;
import mvc.spring.blogsapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{userId}")
    public User getUser(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
        User created = userService.createUser(user);
        URI location = MvcUriComponentsBuilder.fromMethodName(UserController.class, "addUser", user)
                .pathSegment("{userId}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody @Valid User user) {
        if (!userId.equals(user.getId())) {
            throw new InvalidEntityException(String.format("Entity IDs %s and %s don't match", userId, user.getId()));
        }
        User updated = userService.updateUser(user);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }
}
