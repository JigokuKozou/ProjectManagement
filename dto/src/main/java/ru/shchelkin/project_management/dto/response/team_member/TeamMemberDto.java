package ru.shchelkin.project_management.dto.response.team_member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shchelkin.project_management.commons.role.TeamRole;
import ru.shchelkin.project_management.dto.response.employee.EmployeeDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberDto {
    private EmployeeDto member;

    private TeamRole role;
}
