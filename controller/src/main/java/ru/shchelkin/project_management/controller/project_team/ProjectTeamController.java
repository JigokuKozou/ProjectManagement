package ru.shchelkin.project_management.controller.project_team;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.shchelkin.project_management.business.service.project_team.ProjectTeamService;
import ru.shchelkin.project_management.dto.request.project_team.AddTeamMemberDto;
import ru.shchelkin.project_management.dto.request.project_team.GetAllTeamMemberDto;
import ru.shchelkin.project_management.dto.request.project_team.RemoveTeamMemberDto;
import ru.shchelkin.project_management.dto.response.team_member.TeamMemberCardDto;

import java.util.List;

@RestController
@RequestMapping("api/team")
public class ProjectTeamController {

    private final ProjectTeamService projectTeamService;

    @Autowired
    public ProjectTeamController(ProjectTeamService projectTeamService) {
        this.projectTeamService = projectTeamService;
    }

    @PatchMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void add(@RequestBody @Valid AddTeamMemberDto addTeamMemberDto) {
        projectTeamService.addTeamMember(addTeamMemberDto);
    }

    @PatchMapping(value = "/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@RequestBody @Valid RemoveTeamMemberDto removeTeamMemberDto) {
        projectTeamService.removeTeamMember(removeTeamMemberDto);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TeamMemberCardDto> getProjectTeamMembers(
            @RequestBody @Valid GetAllTeamMemberDto getAllTeamMemberDto) {
        return projectTeamService.getAll(getAllTeamMemberDto);
    }
}
