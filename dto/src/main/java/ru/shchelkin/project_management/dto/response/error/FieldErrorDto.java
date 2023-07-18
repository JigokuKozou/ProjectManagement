package ru.shchelkin.project_management.dto.response.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error info with field")
public class FieldErrorDto {

    @Schema(description = "Field name")
    private String field;

    @Schema(description = "Message")
    private String message;
}
