package mvc.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.exception.InvalidEntityIdException;
import mvc.spring.restmvc.model.User;
import mvc.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(value = {"", "/"})
    public List<User> getUsers() {
        return service.getAllUsers();
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable("id") String id) {
        return service.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User created = service.createUser(user);
        URI location = MvcUriComponentsBuilder
                .fromMethodName(UserController.class, "addUser", User.class)
                .pathSegment("{id}")
                .buildAndExpand(created.getId())
                .toUri();
        log.info("User created: {}", location);
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable("id") String id, @Valid @RequestBody User user) {
        if (!id.equals(user.getId())) {
            throw new InvalidEntityIdException(String.format("Entity ID='%s' is different from URL resource ID='%s'", user.getId(), id));
        } else {
            User updated = service.updateUser(user);
            log.info("User updated: {}", updated);
            return ResponseEntity.ok(updated);
        }
    }

    @DeleteMapping("{id}")
    public User remove(@PathVariable("id") String id) {
        return service.deleteUser(id);
    }
}
