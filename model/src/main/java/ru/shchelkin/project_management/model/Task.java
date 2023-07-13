package ru.shchelkin.project_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.shchelkin.project_management.commons.status.TaskStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "Id should be positive")
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name should not be blank")
    @Size(max = 100, message = "Name should not be more than 100 symbols")
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private TeamMember executor;

    @Column(name = "estimate_hours")
    private Integer estimateHours;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private TeamMember author;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
