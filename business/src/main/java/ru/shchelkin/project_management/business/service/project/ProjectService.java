package ru.shchelkin.project_management.business.service.project;

import ru.shchelkin.project_management.dto.request.project.ChangeProjectStatusDto;
import ru.shchelkin.project_management.dto.request.project.CreateProjectDto;
import ru.shchelkin.project_management.dto.request.project.SearchProjectDto;
import ru.shchelkin.project_management.dto.request.project.UpdateProjectDto;
import ru.shchelkin.project_management.dto.response.project.ProjectDto;

import java.util.List;

public interface ProjectService {
    ProjectDto create(CreateProjectDto createProjectDto);

    ProjectDto update(UpdateProjectDto updateProjectDto);

    List<ProjectDto> getAll(SearchProjectDto searchProjectDto);

    void changeStatus(ChangeProjectStatusDto changeProjectStatusDto);
}
