package demo.todolist.helper.shared;

import javax.validation.ConstraintViolationException;

import demo.todolist.helper.tasks.TaskAlreadyExistsException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Exception",
                e.getMessage()
        );
        return apiError.response();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found Exception",
                e.getMessage()
        );
        return apiError.response();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(TaskAlreadyExistsException.class)
    ResponseEntity<Object> handleDataIntegrityViolationException(TaskAlreadyExistsException e) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Task Already Exists Exception",
                e.getMessage()
        );
        return apiError.response();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleInternalException(Exception e) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Exception",
                "The application was not able to process the request properly"
        );
        return apiError.response();
    }

}
