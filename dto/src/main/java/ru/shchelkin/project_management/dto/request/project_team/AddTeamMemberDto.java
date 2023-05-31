package ru.shchelkin.project_management.dto.request.project_team;

import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.role.TeamRole;

@Getter
@Setter
public class AddTeamMemberDto {
    private String projectCodeName;

    private Long employeeId;

    private TeamRole role;
}
