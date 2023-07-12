package ru.shchelkin.project_management.business.mapper;

import ru.shchelkin.project_management.dto.request.project.CreateProjectDto;
import ru.shchelkin.project_management.dto.response.project.ProjectCardDto;
import ru.shchelkin.project_management.model.Project;

public class ProjectMapper {
    public static ProjectCardDto getProjectCard(Project project) {
        final ProjectCardDto projectDto = new ProjectCardDto();

        map(project, projectDto);

        return projectDto;
    }

    public static void map(CreateProjectDto from, Project to) {
        to.setCodeName(from.getCodeName());
        to.setName(from.getName());
        to.setDescription(from.getDescription());
    }

    public static void map(Project from, ProjectCardDto to) {
        to.setCodeName(from.getCodeName());
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setStatus(from.getStatus());
    }
}
