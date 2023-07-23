package ru.shchelkin.project_management.business.mapper;

import ru.shchelkin.project_management.dto.response.employee.EmployeeDto;
import ru.shchelkin.project_management.dto.response.team_member.TeamMemberDto;
import ru.shchelkin.project_management.model.TeamMember;

public class TeamMemberMapper {
    public static TeamMemberDto getTeamMemberDto(TeamMember teamMember) {
        final TeamMemberDto teamMemberDto = new TeamMemberDto();

        map(teamMember, teamMemberDto);

        return teamMemberDto;
    }

    public static void map(TeamMember from, TeamMemberDto to) {
        final EmployeeDto employeeDto = EmployeeDtoMapper.getEmployeeDto(from.getEmployee());

        to.setMember(employeeDto);
        to.setRole(from.getRole());
    }
}
