package ru.shchelkin.project_management.business.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.shchelkin.project_management.business.mapper.ProjectMapper;
import ru.shchelkin.project_management.commons.exceptions.BlankException;
import ru.shchelkin.project_management.commons.exceptions.MaxLengthException;
import ru.shchelkin.project_management.commons.exceptions.NotUniqueException;
import ru.shchelkin.project_management.commons.exceptions.project.ProjectIllegalStatusException;
import ru.shchelkin.project_management.commons.exceptions.project.ProjectNotFoundException;
import ru.shchelkin.project_management.commons.status.ProjectStatus;
import ru.shchelkin.project_management.dao.project.ProjectRepository;
import ru.shchelkin.project_management.dao.project.specification.ProjectSpecification;
import ru.shchelkin.project_management.dto.request.project.ChangeProjectStatusDto;
import ru.shchelkin.project_management.dto.request.project.CreateProjectDto;
import ru.shchelkin.project_management.dto.request.project.SearchProjectDto;
import ru.shchelkin.project_management.dto.request.project.UpdateProjectDto;
import ru.shchelkin.project_management.dto.response.project.ProjectDto;
import ru.shchelkin.project_management.model.Project;

import java.util.List;

@Service
public class ProjectJpaService implements ProjectService{

    public static final int CODE_NAME_MAX_LENGTH = 100;
    public static final int NAME_MAX_LENGTH = 100;

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectJpaService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public ProjectDto create(CreateProjectDto createProjectDto) {
        validateCodeName(createProjectDto.getCodeName());
        validateName(createProjectDto.getName());

        projectRepository.findByCodeName(createProjectDto.getCodeName())
                .ifPresent(another -> { throw new NotUniqueException("codeName", "Code name"); });

        final Project project = new Project();

        ProjectMapper.map(createProjectDto, project);

        project.setStatus(ProjectStatus.DRAFT);

        return ProjectMapper.getProjectDto(projectRepository.saveAndFlush(project));
    }

    @Override
    @Transactional
    public ProjectDto update(UpdateProjectDto updateProjectDto) {
        validateCodeName(updateProjectDto.getCodeName());
        validateName(updateProjectDto.getName());

        final Project project = getByCodeName(updateProjectDto.getCodeName());

        ProjectMapper.map(updateProjectDto, project);

        return ProjectMapper.getProjectDto(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectDto> getAll(SearchProjectDto searchProjectDto) {
        return projectRepository.findAll(ProjectSpecification.get(searchProjectDto))
                .stream().map(ProjectMapper::getProjectDto)
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

    private void validateCodeName(String codeName) {
        codeName = codeName.strip();

        if (!StringUtils.hasText(codeName))
            throw new BlankException("codeName", "Code name");

        if (codeName.length() > CODE_NAME_MAX_LENGTH)
            throw new MaxLengthException("codeName", CODE_NAME_MAX_LENGTH, "Code name");
    }

    private void validateName(String name) {
        if (!StringUtils.hasText(name))
            throw new BlankException("name");

        if (name.strip().length() > NAME_MAX_LENGTH)
            throw new MaxLengthException("name", NAME_MAX_LENGTH);
    }
}
