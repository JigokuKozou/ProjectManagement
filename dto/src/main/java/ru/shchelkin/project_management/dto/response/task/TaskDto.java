package ru.shchelkin.project_management.dto.response.task;

import lombok.*;
import ru.shchelkin.project_management.commons.status.TaskStatus;
import ru.shchelkin.project_management.dto.response.team_member.TeamMemberDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;

    private String name;

    private String description;

    private TeamMemberDto executor;

    private Integer estimateHours;

    private LocalDateTime deadlineDate;

    private TaskStatus status;

    private TeamMemberDto author;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
