package ru.shchelkin.project_management.dto.request.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shchelkin.project_management.commons.status.ProjectStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchProjectDto {
    private String filter;

    private List<ProjectStatus> statuses;
}
