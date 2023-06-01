package ru.shchelkin.project_management.dto.response.project_team;

import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.dto.response.team_member.TeamMemberCardDto;

import java.util.Set;

@Getter
@Setter
public class ProjectTeamCardDto {
    private String projectCodeName;

    private Set<TeamMemberCardDto> members;
}
