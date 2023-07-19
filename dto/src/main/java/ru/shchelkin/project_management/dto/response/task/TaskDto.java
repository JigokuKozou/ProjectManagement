package ru.shchelkin.project_management.dto.response.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.shchelkin.project_management.commons.status.TaskStatus;
import ru.shchelkin.project_management.dto.response.team_member.TeamMemberDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Task info")
public class TaskDto {

    @Schema(description = "Unique identifier")
    private Long id;

    @Schema(description = "Name")
    private String name;

    @Schema(description = "Description")
    private String description;

    @Schema(description = "Author")
    private TeamMemberDto author;

    @Schema(description = "Executor")
    private TeamMemberDto executor;

    @Schema(description = "Estimate hours")
    private Integer estimateHours;

    @Schema(description = "Deadline date (UTC time zone)")
    private LocalDateTime deadlineDate;

    @Schema(description = "Status")
    private TaskStatus status;

    @Schema(description = "Created at (UTC time zone)")
    private LocalDateTime createdAt;

    @Schema(description = "Updated at (UTC time zone)")
    private LocalDateTime updatedAt;
}
