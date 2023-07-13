package ru.shchelkin.project_management.dto.request.project;

import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.status.ProjectStatus;

import java.util.List;

@Getter
@Setter
public class SearchProjectDto {
    private String filter;

    private List<ProjectStatus> statuses;
}
