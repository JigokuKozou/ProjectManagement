package ru.shchelkin.project_management.dto.request.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.status.TaskStatus;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Task info to change status")
public class ChangeTaskStatusDto {

    @Schema(description = "Unique identifier")
    @Positive(message = "Id should be positive")
    private Long id;

    @Schema(description = "Status")
    @NotNull(message = "Status should not be null")
    private TaskStatus status;
}
