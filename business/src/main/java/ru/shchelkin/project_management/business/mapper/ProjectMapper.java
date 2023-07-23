package ru.shchelkin.project_management.business.mapper;

import ru.shchelkin.project_management.commons.util.CustomStringUtils;
import ru.shchelkin.project_management.dto.request.project.CreateProjectDto;
import ru.shchelkin.project_management.dto.response.project.ProjectDto;
import ru.shchelkin.project_management.model.Project;

public class ProjectMapper {
    public static ProjectDto getProjectDto(Project project) {
        final ProjectDto projectDto = new ProjectDto();

        map(project, projectDto);

        return projectDto;
    }

    public static void map(CreateProjectDto from, Project to) {
        to.setCodename(CustomStringUtils.strip(from.getCodename()));
        to.setName(CustomStringUtils.strip(from.getName()));
        to.setDescription(CustomStringUtils.strip(from.getDescription()));
    }

    public static void map(Project from, ProjectDto to) {
        to.setCodename(from.getCodename());
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setStatus(from.getStatus());
    }
}
