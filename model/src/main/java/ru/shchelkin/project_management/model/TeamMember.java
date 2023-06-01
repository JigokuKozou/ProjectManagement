package ru.shchelkin.project_management.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shchelkin.project_management.commons.role.TeamRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamMember {
    private Long id;

    private Employee member;

    private ProjectTeam team;

    private TeamRole role;
}
