package ru.shchelkin.project_management.dto.response.task;

import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.status.TaskStatus;
import ru.shchelkin.project_management.dto.response.team_member.TeamMemberCardDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskCardDto {
    private Long id;

    private String name;

    private String description;

    private TeamMemberCardDto executor;

    private Integer estimateHours;

    private LocalDateTime deadLineDate;

    private TaskStatus status;

    private TeamMemberCardDto author;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
