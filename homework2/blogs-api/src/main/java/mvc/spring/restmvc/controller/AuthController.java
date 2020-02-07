package mvc.spring.restmvc.controller;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.forms.LoginForm;
import mvc.spring.restmvc.forms.RegisterForm;
import mvc.spring.restmvc.model.User;
import mvc.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

@Controller
@Slf4j
public class AuthController {

    private static final String UPLOADS_DIR = "tmp";

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
    public String registerPage(@Valid RegisterForm registerForm, BindingResult bindingResult, @RequestParam("file") MultipartFile file, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/api/register?error";
        }
        User user = new User();
        if (!file.isEmpty() && file.getOriginalFilename().length() > 0) {
            if (Pattern.matches("\\w+\\.(jpg|png)", file.getOriginalFilename())) {
                handleMultipartFile(file);
                user.setImageUrl(file.getOriginalFilename());
            } else {
                model.addAttribute("fileError", "Submit picture [.jpg, .png]");
                return "redirect:/api/register?error";
            }
        }
        user.setFirstName(registerForm.getFirstName());
        user.setLastName(registerForm.getLastName());
        user.setEmail(registerForm.getEmail());
        user.setPassword(registerForm.getPassword());
        userService.createUser(user);
        return "redirect:/api/register?success";
    }

    private void handleMultipartFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        long size = file.getSize();
        log.info("File: " + name + ", Size: " + size);
        try {
            File currentDir = new File(UPLOADS_DIR);
            if(!currentDir.exists()) {
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
