package mvc.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.exception.InvalidEntityIdException;
import mvc.spring.restmvc.model.User;
import mvc.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private static final String UPLOADS_DIR = "tmp";

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"", "/"})
    @CrossOrigin
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @CrossOrigin
    @GetMapping("{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @CrossOrigin
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User created = userService.createUser(user);
        URI location = MvcUriComponentsBuilder
                .fromMethodName(UserController.class, "addUser", User.class)
                .pathSegment("{id}")
                .buildAndExpand(created.getId())
                .toUri();
        log.info("User created: {}", location);
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("{id}")
    @CrossOrigin
    public ResponseEntity<User> update(@PathVariable("id") Long id, @Valid @RequestBody User user) {
        if (!id.equals(user.getId())) {
            throw new InvalidEntityIdException(String.format("Entity ID='%s' is different from URL resource ID='%s'", user.getId(), id));
        } else {
//            if (!file.isEmpty() && file.getOriginalFilename().length() > 0) {
//                if (Pattern.matches("\\w+\\.(jpg|png)", file.getOriginalFilename())) {
//                    handleMultipartFile(file);
//                    user.setImageUrl(file.getOriginalFilename());
//                } else {
//                    user.setImageUrl(null);
//                }
//            }
            User updated = userService.updateUser(user);
            log.info("User updated: {}", updated);
            return ResponseEntity.ok(updated);
        }
    }

    @DeleteMapping("{id}")
    public User remove(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    private void handleMultipartFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        long size = file.getSize();
        log.info("File: " + name + ", Size: " + size);
        try {
            File currentDir = new File(UPLOADS_DIR);
            if (!currentDir.exists()) {
                currentDir.mkdirs();
            }
            String path = currentDir.getAbsolutePath() + "/" + file.getOriginalFilename();
            path = new File(path).getAbsolutePath();
            log.info(path);
            File f = new File(path);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(f));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
