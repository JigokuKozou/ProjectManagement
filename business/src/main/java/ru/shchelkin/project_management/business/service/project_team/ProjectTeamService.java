package ru.shchelkin.project_management.business.service.project_team;

import ru.shchelkin.project_management.dto.request.project_team.AddTeamMemberDto;
import ru.shchelkin.project_management.dto.request.project_team.GetAllTeamMemberDto;
import ru.shchelkin.project_management.dto.request.project_team.RemoveTeamMemberDto;
import ru.shchelkin.project_management.dto.response.team_member.TeamMemberDto;

import java.util.List;

public interface ProjectTeamService {
    void addTeamMember(AddTeamMemberDto addTeamMemberDto);

    void removeTeamMember(RemoveTeamMemberDto removeTeamMemberDto);

    List<TeamMemberDto> getAll(GetAllTeamMemberDto getAllTeamMemberDto);
}
