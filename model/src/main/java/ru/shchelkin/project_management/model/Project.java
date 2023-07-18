package ru.shchelkin.project_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shchelkin.project_management.commons.status.ProjectStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "Id should be positive")
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Codename should not be blank")
    @Size(max = 100, message = "Codename should not be more than 100 symbols")
    @Column(name = "codename")
    private String codename;

    @NotBlank(message = "Name should not be blank")
    @Size(max = 100, message = "Name should not be more than 100 symbols")
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull(message = "Status should not be null")
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private ProjectStatus status;

    @OneToOne(mappedBy = "project")
    private ProjectTeam team;
}
