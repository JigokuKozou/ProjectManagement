package ru.shchelkin.project_management.dto.response.project;

import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.status.ProjectStatus;

@Getter
@Setter
public class ProjectCardDto {
    private String codeName;

    private String name;

    private String description;

    private ProjectStatus status;
}
