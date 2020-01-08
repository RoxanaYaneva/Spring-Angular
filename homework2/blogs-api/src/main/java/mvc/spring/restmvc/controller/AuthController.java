package mvc.spring.restmvc.controller;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.forms.LoginForm;
import mvc.spring.restmvc.forms.RegisterForm;
import mvc.spring.restmvc.model.User;
import mvc.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/login", method = RequestMethod.GET)
    public String login(LoginForm loginForm) {
        return "users/login";
    }

    @RequestMapping(value = "/api/register", method = RequestMethod.GET)
    public String register(RegisterForm registerForm) {
        return "users/register";
    }

    @RequestMapping(value = "/api/register", method = RequestMethod.POST)
    public String registerPage(@Valid RegisterForm registerForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.getAllErrors().toString());
            return "redirect:/api/register?error";
        }
        User user = new User();
        user.setFirstName(registerForm.getFirstName());
        user.setLastName(registerForm.getLastName());
        user.setEmail(registerForm.getEmail());
        user.setPassword(registerForm.getPassword());
        userService.createUser(user);
        return "redirect:/api/register?success";
    }
}
