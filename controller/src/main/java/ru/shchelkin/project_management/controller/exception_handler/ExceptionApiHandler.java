package ru.shchelkin.project_management.controller.exception_handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.shchelkin.project_management.commons.exceptions.ArgumentException;
import ru.shchelkin.project_management.commons.exceptions.ArgumentsException;
import ru.shchelkin.project_management.commons.exceptions.NotFoundException;
import ru.shchelkin.project_management.dto.response.error.ErrorDto;
import ru.shchelkin.project_management.dto.response.error.FieldErrorDto;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionApiHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        List<FieldErrorDto> errors = ex.getBindingResult().getAllErrors().stream()
                .map(objectError -> (FieldError) objectError)
                .map(fieldError ->
                        new FieldErrorDto(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDto> handleException(NotFoundException ex) {
        return new ResponseEntity<>(new ErrorDto(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDto> handleException(IllegalStateException ex) {
        return new ResponseEntity<>(new ErrorDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ArgumentException.class)
    public ResponseEntity<FieldErrorDto> handleException(ArgumentException ex) {
        return new ResponseEntity<>(new FieldErrorDto(ex.getArgumentName(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ArgumentsException.class)
    public ResponseEntity<List<FieldErrorDto>> handleException(ArgumentsException ex) {
        final List<FieldErrorDto> fieldErrorDtos = new ArrayList<>();
        for (var error: ex.getArgumentsError().entrySet()) {
            fieldErrorDtos.add(new FieldErrorDto(error.getKey(), error.getValue()));
        }

        return new ResponseEntity<>(fieldErrorDtos, HttpStatus.BAD_REQUEST);
    }
}
