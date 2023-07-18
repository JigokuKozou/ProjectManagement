package ru.shchelkin.project_management.dto.response.project_team;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Project team info")
public class ProjectTeamDto {

    @Schema(description = "Project unique codename")
    private String projectCodename;

    @Schema(description = "Members")
    private Set<TeamMemberDto> members;
}
