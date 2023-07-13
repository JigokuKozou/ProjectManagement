package ru.shchelkin.project_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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


    @NotNull(message = "Project should not be null")
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private TeamMember executor;

    @Positive(message = "Estimate hours should be positive")
    @Column(name = "estimate_hours")
    private Integer estimateHours;

    @Future(message = "Deadline should not be in past or now")
    @Column(name = "deadline")
    private LocalDateTime deadline;

    @NotNull(message = "Status should not be null")
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private TaskStatus status;

    @NotNull(message = "Author should not be null")
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private TeamMember author;

    @PastOrPresent
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PastOrPresent
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
