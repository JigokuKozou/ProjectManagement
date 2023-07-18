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

    @Schema(description = "Unique codename")
    @NotBlank(message = "Codename should not be blank")
    @Size(min = 1, max = 100, message = "Codename should not be more than 100 symbols")
    private String codename;

    @Schema(description = "New status")
    @NotNull(message = "Status should not be null")
    private ProjectStatus status;
}
