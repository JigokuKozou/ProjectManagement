package ru.shchelkin.project_management.dto.request.task;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.status.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SearchTaskDto {

    @Size(max = 100, message = "Name should not be more than 100 symbols")
    private String name;

    private List<TaskStatus> statuses;

    @Positive(message = "Executor id should be positive")
    private Long executorId;

    @Positive(message = "Author id should be positive")
    private Long authorId;

    private LocalDateTime deadLineTimeStart;

    private LocalDateTime deadLineTimeEnd;

    private LocalDateTime startTaskTimeStart;

    private LocalDateTime startTaskTimeEnd;
}
