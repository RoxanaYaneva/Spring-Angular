package mvc.spring.restmvc.controller;

import mvc.spring.restmvc.exception.EntityNotFoundException;
import mvc.spring.restmvc.exception.InvalidEntityIdException;
import mvc.spring.restmvc.exception.UserAlreadyExistsException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = {mvc.spring.restmvc.controller.PostController.class})
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler
    public String handleExceptions(EntityNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler
    public String handleExceptions(InvalidEntityIdException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler
    public String handleExceptions(UserAlreadyExistsException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
