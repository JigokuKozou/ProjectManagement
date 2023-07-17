package ru.shchelkin.project_management.dto.response.project;

import lombok.*;
import ru.shchelkin.project_management.commons.status.ProjectStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private String codeName;

    private String name;

    private String description;

    private ProjectStatus status;
}
