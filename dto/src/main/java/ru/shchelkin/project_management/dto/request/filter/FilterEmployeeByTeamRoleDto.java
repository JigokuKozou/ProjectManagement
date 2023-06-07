package ru.shchelkin.project_management.dto.request.filter;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.role.TeamRole;

@Getter
@Setter
public class FilterEmployeeByTeamRoleDto {
    @Size(max = 100, message = "Project code name should not be more than 100 symbols")
    private String projectCodeName;

    private TeamRole teamRole;
}
