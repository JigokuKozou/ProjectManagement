package ru.shchelkin.project_management.dto.request.project_team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.shchelkin.project_management.commons.role.TeamRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddTeamMemberDto {

    @NotBlank(message = "Project codename should not be blank")
    @Size(max = 100, message = "Project codename should not be more than 100 symbols")
    private String projectCodename;

    @NotNull(message = "Employee id should not be null")
    @Positive(message = "Employee id should be positive")
    private Long employeeId;

    @NotNull(message = "Role should not be null")
    private TeamRole role;
}
