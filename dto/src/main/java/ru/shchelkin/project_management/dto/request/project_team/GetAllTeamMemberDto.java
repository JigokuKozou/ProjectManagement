package ru.shchelkin.project_management.dto.request.project_team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAllTeamMemberDto {

    @NotBlank(message = "Project code name should not be blank")
    @Size(max = 100, message = "Project code name should not be more than 100 symbols")
    private String projectCodeName;
}
