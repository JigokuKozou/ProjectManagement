package ru.shchelkin.project_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project_team")
public class ProjectTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @OneToMany(mappedBy = "team")
    private Set<TeamMember> members;

    public boolean add(TeamMember member) {
        if (Objects.isNull(members))
            members = new HashSet<>();

        return members.add(member);
    }

    public boolean remove(TeamMember member) {
        if (Objects.isNull(members))
            return false;

        return members.remove(member);
    }
}
