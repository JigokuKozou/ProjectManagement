package ru.shchelkin.project_management.business.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchelkin.project_management.business.mapper.ProjectMapper;
import ru.shchelkin.project_management.commons.exceptions.project.ProjectIllegalStatusException;
import ru.shchelkin.project_management.commons.exceptions.project.ProjectNotFoundException;
import ru.shchelkin.project_management.commons.status.ProjectStatus;
import ru.shchelkin.project_management.dao.project.ProjectRepository;
import ru.shchelkin.project_management.dao.project.specification.ProjectSpecification;
import ru.shchelkin.project_management.dto.request.project.ChangeProjectStatusDto;
import ru.shchelkin.project_management.dto.request.project.CreateProjectDto;
import ru.shchelkin.project_management.dto.request.project.SearchProjectDto;
import ru.shchelkin.project_management.dto.request.project.UpdateProjectDto;
import ru.shchelkin.project_management.dto.response.project.ProjectCardDto;
import ru.shchelkin.project_management.model.Project;

import java.util.List;

@Service
public class ProjectJpaService implements ProjectService{
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectJpaService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public ProjectCardDto create(CreateProjectDto createProjectDto) {
        final Project project = new Project();
        ProjectMapper.map(createProjectDto, project);

        return ProjectMapper.getProjectCard(projectRepository.save(project));
    }

    @Override
    @Transactional
    public ProjectCardDto update(UpdateProjectDto updateProjectDto) {
        final Project project = getByCodeName(updateProjectDto.getCodeName());

        ProjectMapper.map(updateProjectDto, project);

        return ProjectMapper.getProjectCard(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCardDto> getAll(SearchProjectDto searchProjectDto) {
        return projectRepository.findAll(ProjectSpecification.get(searchProjectDto))
                .stream().map(ProjectMapper::getProjectCard)
                .toList();
    }

    @Override
    @Transactional
    public void changeStatus(ChangeProjectStatusDto changeProjectStatusDto) {
        final Project project = getByCodeName(changeProjectStatusDto.getCodeName());

        if (isAvailableChangeStatus(project.getStatus(), changeProjectStatusDto.getStatus()))
            project.setStatus(changeProjectStatusDto.getStatus());
        else
            throw new ProjectIllegalStatusException(project.getStatus(), changeProjectStatusDto.getStatus());
    }

    private Project getByCodeName(String codeName) {
        return projectRepository.findByCodeName(codeName)
                .orElseThrow(ProjectNotFoundException::new);
    }

    private static boolean isAvailableChangeStatus(ProjectStatus current, ProjectStatus value) {
        return switch (current) {
            case DRAFT -> value == ProjectStatus.IN_PROGRESS;
            case IN_PROGRESS -> value == ProjectStatus.TESTING;
            case TESTING -> value == ProjectStatus.DONE;
            case DONE -> false;
        };
    }
}
