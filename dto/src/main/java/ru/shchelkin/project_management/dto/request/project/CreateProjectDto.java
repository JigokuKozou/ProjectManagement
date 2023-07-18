package ru.shchelkin.project_management.dto.request.project;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Project info to create")
public class CreateProjectDto {

    @Schema(description = "Unique codename")
    @NotBlank(message = "Codename should not be blank")
    @Size(min = 1, max = 100, message = "Codename should not be more than 100 symbols")
    private String codename;

    @Schema(description = "Name")
    @NotBlank(message = "Name should not be blank")
    @Size(min = 1, max = 100, message = "Name should not be more than 100 symbols")
    private String name;

    @Schema(description = "Description")
    private String description;
}
