package ru.shchelkin.project_management.dto.response.team_member;

import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.role.TeamRole;
import ru.shchelkin.project_management.dto.response.employee.EmployeeCardDto;

@Getter
@Setter
public class TeamMemberCardDto {
    private EmployeeCardDto member;

    private TeamRole role;
}
