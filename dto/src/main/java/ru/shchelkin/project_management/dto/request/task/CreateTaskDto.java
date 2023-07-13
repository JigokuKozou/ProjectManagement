package ru.shchelkin.project_management.dto.request.task;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateTaskDto {
    private String projectCodeName;

    private String name;

    private String description;

    private Long executorId;

    private Integer estimateHours;

    private LocalDateTime deadLineDate;

    private Long authorId;
}
