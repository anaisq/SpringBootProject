package main.exception.advice;

import jakarta.persistence.EntityNotFoundException;
import main.exception.UserAlreadyOnDbException;
import main.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({UserNotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<String > handleUserNotFound(Exception exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage() + " at " + LocalDateTime.now());
    }

    @ExceptionHandler(UserAlreadyOnDbException.class)
    public ResponseEntity<String> handleUserAlreadyOnDb(Exception exception){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleInvalidField(MethodArgumentNotValidException exception) {

        System.out.println(exception.getMessage());

        String invalidFields = "Invalid fields: \n"
                + exception.getBindingResult().getFieldErrors().stream()
                .map(e -> "Field: " + e.getField() + ", error: " + e.getDefaultMessage() + ", value: " + e.getRejectedValue())
                .collect(Collectors.joining("\n"));
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(invalidFields);
    }


}
