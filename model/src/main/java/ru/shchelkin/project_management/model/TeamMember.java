package ru.shchelkin.project_management.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shchelkin.project_management.commons.role.TeamRole;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team_member")
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "Id should be positive")
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Employee should not be null")
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @NotNull(message = "Team should not be null")
    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private ProjectTeam team;

    @NotNull(message = "Team role should not be null")
    @Column(name = "role")
    @Enumerated(EnumType.ORDINAL)
    private TeamRole role;
}
