package ru.shchelkin.project_management.dto.request.task;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.status.TaskStatus;

@Getter
@Setter
@AllArgsConstructor
public class ChangeTaskStatusDto {

    @Positive(message = "Id should be positive")
    private Long id;

    @NotNull(message = "Status should not be null")
    private TaskStatus status;
}
