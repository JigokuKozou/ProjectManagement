package ru.shchelkin.project_management.dto.response.team_member;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Team member info")
public class TeamMemberDto {

    @Schema(description = "Employee info")
    private EmployeeDto member;

    @Schema(description = "Team role")
    private TeamRole role;
}
