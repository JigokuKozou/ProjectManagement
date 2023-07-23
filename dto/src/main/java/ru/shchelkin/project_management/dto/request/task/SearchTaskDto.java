package ru.shchelkin.project_management.dto.request.task;

import lombok.*;
import ru.shchelkin.project_management.commons.status.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchTaskDto {

    private String name;

    private List<TaskStatus> statuses;

    private Long executorId;

    private Long authorId;

    private LocalDateTime deadlineStart;

    private LocalDateTime deadlineEnd;

    private LocalDateTime createdAtStart;

    private LocalDateTime createdAtEnd;
}
