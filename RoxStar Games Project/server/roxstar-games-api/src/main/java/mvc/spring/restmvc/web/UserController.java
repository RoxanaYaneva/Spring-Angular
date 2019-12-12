package mvc.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.exception.InvalidEntityIdException;
import mvc.spring.restmvc.model.User;
import mvc.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public User addUser() {
        return null;
    }

    @PutMapping("{id}")
    public User update(@PathVariable("id") String id, @Valid User user) {
        if (id.equals(user.getId())) {
            throw new InvalidEntityIdException(String.format("Entity ID='%s' is different from URL resource ID='%s'", user.getId(), id));
        } else {
            return service.updateUser(user);
        }
    }

    @DeleteMapping("{id}")
    public User remove(@PathVariable("id") String id) {
        return service.deleteUser(id);
    }
}
