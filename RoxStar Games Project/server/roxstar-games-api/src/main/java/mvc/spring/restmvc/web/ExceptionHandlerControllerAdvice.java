package mvc.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.exception.EntityNotFoundException;
import mvc.spring.restmvc.exception.InvalidEntityIdException;
import mvc.spring.restmvc.exception.EntityAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;

@Slf4j
@ControllerAdvice("mvc.spring.restmvc")
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handle(EntityNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({InvalidEntityIdException.class, ConstraintViolationException.class})
    public ResponseEntity<String> handle(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<String> handle(EntityAlreadyExistsException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(AccessDeniedException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
