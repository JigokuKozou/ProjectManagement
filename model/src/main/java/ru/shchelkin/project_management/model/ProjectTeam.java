package ru.shchelkin.project_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @Positive(message = "Id should be positive")
    @Column(name = "id")
    private Long id;


    @NotNull(message = "Project should not be null")
    @OneToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @OneToMany(mappedBy = "team")
    private Set<TeamMember> members;

    public boolean add(TeamMember newMember) {
        if (Objects.isNull(members))
            members = new HashSet<>();

        for (TeamMember member: members) {
            if ((member == newMember) ||
                    (member.getEmployee() == newMember.getEmployee()))
                return false;
        }

        return members.add(newMember);
    }

    public boolean remove(TeamMember member) {
        if (Objects.isNull(members))
            return false;

        return members.remove(member);
    }
}
