package ru.shchelkin.project_management.controller.exception_handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.shchelkin.project_management.commons.exceptions.IllegalStatusException;
import ru.shchelkin.project_management.commons.exceptions.NotFoundException;
import ru.shchelkin.project_management.commons.exceptions.NotValidException;
import ru.shchelkin.project_management.commons.exceptions.WasNotRemovedException;
import ru.shchelkin.project_management.dto.response.error.FieldErrorDto;

import java.util.HashMap;

@RestControllerAdvice
public class ConstraintsErrorsHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        return getErrorResponseEntity(ex.getBindingResult());
    }

    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<Object> handleException(NotValidException exception) {
        return getErrorResponseEntity(exception.getBindingResult());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleException(NotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStatusException.class)
    public ResponseEntity<Object> handleException(IllegalStatusException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WasNotRemovedException.class)
    public ResponseEntity<Object> handleException(WasNotRemovedException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private static ResponseEntity<Object> getErrorResponseEntity(BindingResult bindingResult) {
        var response = new HashMap<String, Object>();

        var errors = bindingResult.getAllErrors().stream()
                .map(objectError -> (FieldError) objectError)
                .map(FieldErrorDto::new)
                .toList();

        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
