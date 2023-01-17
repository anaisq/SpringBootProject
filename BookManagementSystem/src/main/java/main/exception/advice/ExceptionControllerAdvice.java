package main.exception.advice;

import main.exception.AlreadyOnDbException;
import main.exception.NotAllowed;
import main.exception.NotEnoughResources;
import main.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({NotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<String > handleNotFound(Exception exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage() + " at " + LocalDateTime.now());
    }

    @ExceptionHandler(AlreadyOnDbException.class)
    public ResponseEntity<String> handleAlreadyOnDb(Exception exception){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(NotEnoughResources.class)
    public ResponseEntity<String> handleNotEnoughResources(Exception exception){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(NotAllowed.class)
    public ResponseEntity<String> handleNotAllowed(Exception exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
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
