package ru.shchelkin.project_management.dto.request.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shchelkin.project_management.commons.role.TeamRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterEmployeeByTeamRoleDto {
    private String projectCodeName;

    private TeamRole teamRole;
}
