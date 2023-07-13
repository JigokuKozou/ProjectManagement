package ru.shchelkin.project_management.business.service.project_team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchelkin.project_management.business.mapper.TeamMemberMapper;
import ru.shchelkin.project_management.commons.exceptions.employee.EmployeeNotFoundException;
import ru.shchelkin.project_management.commons.exceptions.project.ProjectNotFoundException;
import ru.shchelkin.project_management.commons.exceptions.project_team.ProjectTeamNotFoundException;
import ru.shchelkin.project_management.commons.exceptions.team_member.TeamMemberNotFoundException;
import ru.shchelkin.project_management.dao.employee.EmployeeRepository;
import ru.shchelkin.project_management.dao.project.ProjectRepository;
import ru.shchelkin.project_management.dao.project_team.ProjectTeamRepository;
import ru.shchelkin.project_management.dao.team_member.TeamMemberRepository;
import ru.shchelkin.project_management.dto.request.project_team.AddTeamMemberDto;
import ru.shchelkin.project_management.dto.request.project_team.GetAllTeamMemberDto;
import ru.shchelkin.project_management.dto.request.project_team.RemoveTeamMemberDto;
import ru.shchelkin.project_management.dto.response.team_member.TeamMemberCardDto;
import ru.shchelkin.project_management.model.Employee;
import ru.shchelkin.project_management.model.Project;
import ru.shchelkin.project_management.model.ProjectTeam;
import ru.shchelkin.project_management.model.TeamMember;

import java.util.List;

@Service
public class ProjectTeamJpaService implements ProjectTeamService{
    private final ProjectTeamRepository teamRepository;

    private final ProjectRepository projectRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public ProjectTeamJpaService(ProjectTeamRepository teamRepository, ProjectRepository projectRepository, TeamMemberRepository teamMemberRepository, EmployeeRepository employeeRepository) {
        this.teamRepository = teamRepository;
        this.projectRepository = projectRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public void addTeamMember(AddTeamMemberDto addTeamMemberDto) {
        final Project project = getProject(addTeamMemberDto.getProjectCodeName());

        final Employee employee = getEmployee(addTeamMemberDto.getEmployeeId());

        final ProjectTeam team = teamRepository.findByProject(project)
                .orElse(createProjectTeam(project));

        final TeamMember member = new TeamMember();
        member.setTeam(team);
        member.setEmployee(employee);
        member.setRole(addTeamMemberDto.getRole());

        teamMemberRepository.save(member);
    }

    @Override
    @Transactional
    public void removeTeamMember(RemoveTeamMemberDto removeTeamMemberDto) {

        final Employee employee = getEmployee(removeTeamMemberDto.getEmployeeId());

        final ProjectTeam team = getProjectTeam(removeTeamMemberDto.getProjectCodeName());

        for (var member : team.getMembers()) {
            if (member.getEmployee() == employee) {
                team.remove(member);

                teamMemberRepository.delete(member);
            }
        }

        throw new TeamMemberNotFoundException();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamMemberCardDto> getAll(GetAllTeamMemberDto getAllTeamMemberDto) {
        final ProjectTeam team = getProjectTeam(getAllTeamMemberDto.getProjectCodeName());

        return team.getMembers().stream()
                .map(TeamMemberMapper::getTeamMemberCardDto)
                .toList();
    }

    private ProjectTeam createProjectTeam(Project project) {
        final ProjectTeam projectTeam = new ProjectTeam();
        projectTeam.setProject(project);

        return teamRepository.save(projectTeam);
    }

    private ProjectTeam getProjectTeam(String projectCodeName) {
        final Project project = getProject(projectCodeName);

        return getProjectTeam(project);
    }

    private Project getProject(String projectCodeName) {
        return projectRepository.findByCodeName(projectCodeName)
                .orElseThrow(ProjectNotFoundException::new);
    }

    private Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
    }

    private ProjectTeam getProjectTeam(Project project) {
        return teamRepository.findByProject(project)
                .orElseThrow(ProjectTeamNotFoundException::new);
    }
}
