package ru.shchelkin.project_management.controller.project;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.shchelkin.project_management.business.service.project.ProjectService;
import ru.shchelkin.project_management.commons.status.ProjectStatus;
import ru.shchelkin.project_management.dto.request.project.ChangeProjectStatusDto;
import ru.shchelkin.project_management.dto.request.project.CreateProjectDto;
import ru.shchelkin.project_management.dto.request.project.SearchProjectDto;
import ru.shchelkin.project_management.dto.request.project.UpdateProjectDto;
import ru.shchelkin.project_management.dto.response.project.ProjectDto;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@Tag(name = "ProjectController", description = "Controller for projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "Create project")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto create(@RequestBody @Valid CreateProjectDto createProjectDto) {
        return projectService.create(createProjectDto);
    }

    @Operation(summary = "Update project info")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectDto update(@RequestBody @Valid UpdateProjectDto updateProjectDto) {
        return projectService.update(updateProjectDto);
    }

    @Operation(summary = "Search projects")
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectDto> search(
            @Parameter(description = "Filter text in project codename/name (case-insensitive)")
            @RequestParam(name = "filter", required = false) String filter,
            @Parameter(description = "List of statuses to search")
            @RequestParam(name = "statuses", required = false)List<ProjectStatus> statuses) {
        final SearchProjectDto searchProjectDto =
                new SearchProjectDto(filter, statuses);

        return projectService.getAll(searchProjectDto);
    }

    @Operation(summary = "Change project status")
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeStatus(@RequestBody @Valid ChangeProjectStatusDto changeProjectStatusDto) {
        projectService.changeStatus(changeProjectStatusDto);
    }
}
