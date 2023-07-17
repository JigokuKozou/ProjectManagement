package ru.shchelkin.project_management.dto.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldErrorDto {
    private String field;

    private String message;

    public FieldErrorDto(FieldError fieldError) {
        field = fieldError.getField();
        message = fieldError.getDefaultMessage();
    }
}
