package ru.shchelkin.project_management.dto.request.project_team;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemoveTeamMemberDto {

    @NotBlank(message = "Project codename should not be blank")
    @Size(max = 100, message = "Project codename should not be more than 100 symbols")
    @Schema(description = "Project unique codename")
    private String projectCodename;

    @NotNull(message = "Employee id should not be null")
    @Positive(message = "Employee id should be positive")
    @Schema(description = "Employee unique identifier")
    private Long employeeId;
}
