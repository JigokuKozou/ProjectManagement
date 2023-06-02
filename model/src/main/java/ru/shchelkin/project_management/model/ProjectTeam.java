package ru.shchelkin.project_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTeam {
    private Project project;

    private Set<TeamMember> members;
}
