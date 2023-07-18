package ru.shchelkin.project_management.business.service.project_team;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.shchelkin.project_management.business.mapper.TeamMemberMapper;
import ru.shchelkin.project_management.commons.exceptions.BlankException;
import ru.shchelkin.project_management.commons.exceptions.NullException;
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
import ru.shchelkin.project_management.dto.response.team_member.TeamMemberDto;
import ru.shchelkin.project_management.model.Employee;
import ru.shchelkin.project_management.model.Project;
import ru.shchelkin.project_management.model.ProjectTeam;
import ru.shchelkin.project_management.model.TeamMember;

import java.util.List;
import java.util.Objects;

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
    public void addTeamMember(@NonNull AddTeamMemberDto addTeamMemberDto) {
        validateProjectCodename(addTeamMemberDto.getProjectCodename());
        validateEmployeeId(addTeamMemberDto.getEmployeeId());

        if (Objects.isNull(addTeamMemberDto.getRole()))
            throw new NullException("role");

        final Project project = getProject(addTeamMemberDto.getProjectCodename());

        final Employee employee = getEmployee(addTeamMemberDto.getEmployeeId());

        final ProjectTeam team = teamRepository.findByProject(project)
                .orElseGet(() -> createProjectTeam(project));

        final TeamMember member = new TeamMember();
        member.setTeam(team);
        member.setEmployee(employee);
        member.setRole(addTeamMemberDto.getRole());

        teamMemberRepository.saveAndFlush(member);
    }

    @Override
    @Transactional
    public void removeTeamMember(@NonNull RemoveTeamMemberDto removeTeamMemberDto) {
        validateProjectCodename(removeTeamMemberDto.getProjectCodename());
        validateEmployeeId(removeTeamMemberDto.getEmployeeId());

        final Employee employee = getEmployee(removeTeamMemberDto.getEmployeeId());

        final ProjectTeam team = getProjectTeam(removeTeamMemberDto.getProjectCodename());

        TeamMember removableMember = null;
        for (var member : team.getMembers()) {
            if (member.getEmployee() == employee) {
                removableMember = member;
                break;
            }
        }

        if (Objects.nonNull(removableMember)) {
            team.remove(removableMember);

            teamMemberRepository.delete(removableMember);
        }
        else throw new TeamMemberNotFoundException();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamMemberDto> getAll(@NonNull GetAllTeamMemberDto getAllTeamMemberDto) {
        final ProjectTeam team = getProjectTeam(getAllTeamMemberDto.getProjectCodename());

        return team.getMembers().stream()
                .map(TeamMemberMapper::getTeamMemberDto)
                .toList();
    }

    private void validateProjectCodename(String projectCodename) {
        if (!StringUtils.hasText(projectCodename))
            throw new BlankException("projectCodename", "Project codename");
    }

    private void validateEmployeeId(Long id) {
        if (Objects.isNull(id))
            throw new NullException("employeeId", "Employee id");
    }

    private ProjectTeam createProjectTeam(Project project) {
        final ProjectTeam projectTeam = new ProjectTeam();
        projectTeam.setProject(project);

        return teamRepository.save(projectTeam);
    }

    private ProjectTeam getProjectTeam(String projectCodename) {
        final Project project = getProject(projectCodename);

        return getProjectTeam(project);
    }

    private Project getProject(String projectCodename) {
        return projectRepository.findByCodename(projectCodename)
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
