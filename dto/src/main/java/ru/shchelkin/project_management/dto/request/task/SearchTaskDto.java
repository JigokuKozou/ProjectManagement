package ru.shchelkin.project_management.dto.request.task;

import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.status.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SearchTaskDto {
    private String name;

    private List<TaskStatus> statuses;

    private Long executorId;

    private Long authorId;

    private LocalDateTime deadLineTimeStart;

    private LocalDateTime deadLineTimeEnd;

    private LocalDateTime startTaskTimeStart;

    private LocalDateTime startTaskTimeEnd;
}
