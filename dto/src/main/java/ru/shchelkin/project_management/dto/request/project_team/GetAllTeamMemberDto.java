package ru.shchelkin.project_management.dto.request.project_team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAllTeamMemberDto {

    private String projectCodename;
}
