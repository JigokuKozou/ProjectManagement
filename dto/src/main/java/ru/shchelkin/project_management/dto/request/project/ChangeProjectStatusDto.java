package ru.shchelkin.project_management.dto.request.project;

import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.status.ProjectStatus;

@Getter
@Setter
public class ChangeProjectStatusDto {
    private String codeName;

    private ProjectStatus status;
}
