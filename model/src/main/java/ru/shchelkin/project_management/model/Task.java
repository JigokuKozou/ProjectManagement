package ru.shchelkin.project_management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shchelkin.project_management.commons.status.TaskStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    private Long id;

    private String name;

    private String description;

    private TeamMember executor;

    private Integer estimate_hours;

    private LocalDateTime deadLineDate;

    private TaskStatus status;

    private TeamMember author;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
