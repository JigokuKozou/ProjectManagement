package ru.shchelkin.project_management.dto.request.task;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateTaskDto {
    private Long id;

    private String name;

    private String description;

    private Long executorId;

    private Integer estimateHours;

    private LocalDateTime deadLineDate;
}
