package ru.shchelkin.project_management.dto.request.project_team;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveTeamMemberDto {
    private String projectCodeName;

    private Long employeeId;
}
