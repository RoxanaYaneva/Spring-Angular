package mvc.spring.restmvc.controller;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.service.UserService;
import mvc.spring.restmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public String getUsers(Model model) {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return "users/list";
        }
        model.addAttribute("users", users);
        return "users/list";
    }

    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/api/users/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable("id") String id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/edit_user";
    }

    @RequestMapping(value = "/api/users/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") String id, Model model) {
        User user = userService.getUserById(id);
        userService.deleteUser(id);
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @RequestMapping(value = "/api/users/activate/{id}", method = RequestMethod.POST)
    public String activateUser(@PathVariable("id") String id, Model model) {
        User user = userService.getUserById(id);
        user.setActive(true);
        userService.updateUser(user);
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @RequestMapping(value = "/api/users/deactivate/{id}", method = RequestMethod.POST)
    public String deactivateUser(@PathVariable("id") String id, Model model) {
        User user = userService.getUserById(id);
        user.setActive(false);
        userService.updateUser(user);
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @RequestMapping(value = "/api/users/update/{id}", method = RequestMethod.POST)
    public String updateUser(@PathVariable("id") String id, @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.getAllErrors().toString());
            return "users/edit_user";
        }
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }
}