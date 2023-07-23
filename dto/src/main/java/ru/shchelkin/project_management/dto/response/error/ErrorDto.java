package ru.shchelkin.project_management.dto.response.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Error info")
public class ErrorDto {

    @Schema(description = "Message")
    private String message;

    public ErrorDto(String message) {
        this.message = message;
    }
}
