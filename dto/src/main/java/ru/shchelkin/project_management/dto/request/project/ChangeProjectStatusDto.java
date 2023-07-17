package ru.shchelkin.project_management.dto.request.project;

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
public class ChangeProjectStatusDto {

    @NotBlank(message = "Code name should not be blank")
    @Size(max = 100, message = "Code name should not be more than 100 symbols")
    private String codeName;

    @NotNull(message = "Status should not be null")
    private ProjectStatus status;
}
