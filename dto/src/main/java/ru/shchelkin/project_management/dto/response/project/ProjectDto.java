package ru.shchelkin.project_management.dto.response.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.shchelkin.project_management.commons.status.ProjectStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Project info")
public class ProjectDto {

    @Schema(description = "Unique codename")
    private String codename;

    @Schema(description = "Name")
    private String name;

    @Schema(description = "Description")
    private String description;

    @Schema(description = "Status")
    private ProjectStatus status;
}
