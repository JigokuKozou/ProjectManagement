package ru.shchelkin.project_management.controller.project_team;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.shchelkin.project_management.business.service.project_team.ProjectTeamService;
import ru.shchelkin.project_management.controller.config.SecurityConfig;
import ru.shchelkin.project_management.dto.request.project_team.AddTeamMemberDto;
import ru.shchelkin.project_management.dto.request.project_team.GetAllTeamMemberDto;
import ru.shchelkin.project_management.dto.request.project_team.RemoveTeamMemberDto;
import ru.shchelkin.project_management.dto.response.team_member.TeamMemberDto;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@Tag(name = "ProjectTeamController", description = "Controller for project teams")
@SecurityRequirement(name = SecurityConfig.SECURITY_CONFIG_NAME)
public class ProjectTeamController {

    private final ProjectTeamService projectTeamService;

    @Autowired
    public ProjectTeamController(ProjectTeamService projectTeamService) {
        this.projectTeamService = projectTeamService;
    }

    @Operation(summary = "Get project team members")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TeamMemberDto> getProjectTeamMembers(
            @Parameter(description = "Project unique codename")
            @RequestParam(value = "projectCodename") String projectCodename) {
        final GetAllTeamMemberDto getAllTeamMemberDto = new GetAllTeamMemberDto(projectCodename);

        return projectTeamService.getAll(getAllTeamMemberDto);
    }

    @Operation(summary = "Add employee to the project")
    @PatchMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void add(@RequestBody @Valid AddTeamMemberDto addTeamMemberDto) {
        projectTeamService.addTeamMember(addTeamMemberDto);
    }

    @Operation(summary = "Remove employee from the project")
    @PatchMapping(value = "/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@RequestBody @Valid RemoveTeamMemberDto removeTeamMemberDto) {
        projectTeamService.removeTeamMember(removeTeamMemberDto);
    }
}
