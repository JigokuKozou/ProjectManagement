package ru.shchelkin.project_management.dto.request.project_team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.role.TeamRole;

@Getter
@Setter
public class AddTeamMemberDto {

    @NotBlank(message = "Project code name should not be blank")
    @Size(max = 100, message = "Project code name should not be more than 100 symbols")
    private String projectCodeName;

    @NotNull(message = "Employee id should not be null")
    @Positive(message = "Employee id should be positive")
    private Long employeeId;

    @NotNull(message = "Role should not be null")
    private TeamRole role;
}
