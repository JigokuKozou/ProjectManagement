package ru.shchelkin.project_management.dto.request.filter;

import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.role.TeamRole;

@Getter
@Setter
public class FilterEmployeeByTeamRoleDto {
    private String projectCodeName;

    private TeamRole teamRole;
}
