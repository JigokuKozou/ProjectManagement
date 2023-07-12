package ru.shchelkin.project_management.business.mapper;

import ru.shchelkin.project_management.dto.response.employee.EmployeeCardDto;
import ru.shchelkin.project_management.dto.response.team_member.TeamMemberCardDto;
import ru.shchelkin.project_management.model.TeamMember;

public class TeamMemberMapper {
    public static TeamMemberCardDto getTeamMemberCardDto(TeamMember teamMember) {
        final TeamMemberCardDto teamMemberCardDto = new TeamMemberCardDto();

        map(teamMember, teamMemberCardDto);

        return teamMemberCardDto;
    }

    public static void map(TeamMember from, TeamMemberCardDto to) {
        final EmployeeCardDto employeeCardDto = EmployeeMapper.getEmployeeCardDto(from.getEmployee());

        to.setMember(employeeCardDto);
        to.setRole(from.getRole());
    }
}
