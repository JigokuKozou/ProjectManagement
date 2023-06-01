package ru.shchelkin.project_management.dto.request.project;

import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.status.ProjectStatus;

@Getter
@Setter
public class UpdateProjectDto {
    private String codeName;

    private String name;

    private String description;

    private ProjectStatus status;
}
