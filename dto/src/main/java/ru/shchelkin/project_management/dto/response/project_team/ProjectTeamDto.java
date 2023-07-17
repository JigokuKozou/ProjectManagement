package ru.shchelkin.project_management.dto.response.project_team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shchelkin.project_management.dto.response.team_member.TeamMemberDto;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTeamDto {
    private String projectCodeName;

    private Set<TeamMemberDto> members;
}
