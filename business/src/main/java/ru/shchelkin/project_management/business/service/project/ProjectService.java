package ru.shchelkin.project_management.business.service.project;

import ru.shchelkin.project_management.dto.request.project.ChangeProjectStatusDto;
import ru.shchelkin.project_management.dto.request.project.CreateProjectDto;
import ru.shchelkin.project_management.dto.request.project.SearchProjectDto;
import ru.shchelkin.project_management.dto.request.project.UpdateProjectDto;
import ru.shchelkin.project_management.dto.response.project.ProjectCardDto;

import java.util.List;

public interface ProjectService {
    ProjectCardDto create(CreateProjectDto createProjectDto);

    ProjectCardDto update(UpdateProjectDto updateProjectDto);

    List<ProjectCardDto> getAll(SearchProjectDto searchProjectDto);

    void changeStatus(ChangeProjectStatusDto changeProjectStatusDto);
}
