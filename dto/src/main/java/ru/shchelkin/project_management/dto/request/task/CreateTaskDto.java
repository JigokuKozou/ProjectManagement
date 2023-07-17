package ru.shchelkin.project_management.dto.request.task;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDto {

    @NotBlank(message = "Project code name should not be blank")
    @Size(max = 100, message = "Project code name should not be more than 100 symbols")
    private String projectCodeName;

    @NotBlank(message = "Name should not be blank")
    @Size(max = 100, message = "Name should not be more than 100 symbols")
    private String name;

    private String description;

    @Positive(message = "Executor id should be positive")
    private Long executorId;

    @NotNull(message = "Estimate hours should not be null")
    @Positive(message = "Estimate hours should be positive")
    private Integer estimateHours;

    @NotNull(message = "Deadline should not be null")
    @Future(message = "Deadline should not be in past or now")
    private LocalDateTime deadlineDate;

    @NotNull(message = "Author id should not be null")
    @Positive(message = "Author id should be positive")
    private Long authorId;
}
