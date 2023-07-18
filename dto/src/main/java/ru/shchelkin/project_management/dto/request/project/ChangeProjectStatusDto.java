package ru.shchelkin.project_management.dto.request.project;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.shchelkin.project_management.commons.status.ProjectStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Project info to change status")
public class ChangeProjectStatusDto {

    @NotBlank(message = "Codename should not be blank")
    @Size(min = 1, max = 100, message = "Codename should not be more than 100 symbols")
    @Schema(description = "Unique codename")
    private String codename;

    @NotNull(message = "Status should not be null")
    @Schema(description = "New status")
    private ProjectStatus status;
}
