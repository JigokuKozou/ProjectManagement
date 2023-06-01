package ru.shchelkin.project_management.dto.request.task;

import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.status.TaskStatus;

@Getter
@Setter
public class ChangeTaskStatusDto {
    private Long id;

    private TaskStatus status;
}
