package ru.shchelkin.project_management.controller.exception_handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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
import ru.shchelkin.project_management.dto.response.error.ErrorDto;
import ru.shchelkin.project_management.dto.response.error.FieldErrorDto;

import java.util.*;

@RestControllerAdvice
public class ExceptionApiHandler extends ResponseEntityExceptionHandler {

    private final static String ERRORS_PROPERTY_NAME = "errors";

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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(ConstraintViolationException exception) {
        final Map<String, Object> response = new HashMap<>(1);

        final Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        final List<FieldErrorDto> fieldErrors = new ArrayList<>(violations.size());
        for (var violation : violations) {
            final FieldErrorDto fieldError = new FieldErrorDto();
            fieldError.setField(violation.getPropertyPath().toString());
            fieldError.setMessage(violation.getMessage());

            fieldErrors.add(fieldError);
        }

        response.put(ERRORS_PROPERTY_NAME, fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(NotFoundException exception) {
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStatusException.class)
    public ResponseEntity<ErrorDto> handleException(IllegalStatusException exception) {
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WasNotRemovedException.class)
    public ResponseEntity<ErrorDto> handleException(WasNotRemovedException exception) {
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private static ResponseEntity<Object> getErrorResponseEntity(BindingResult bindingResult) {
        var response = new HashMap<String, Object>(1);

        var errors = bindingResult.getAllErrors().stream()
                .map(objectError -> (FieldError) objectError)
                .map(FieldErrorDto::new)
                .toList();

        response.put(ERRORS_PROPERTY_NAME, errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
