package ru.shchelkin.project_management.dto.response.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDto {

    private String message;

    public ErrorDto(String message) {
        this.message = message;
    }
}
