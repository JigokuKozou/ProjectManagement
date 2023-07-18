package ru.shchelkin.project_management.dto.request.task;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Task info to update")
public class UpdateTaskDto {

    @Positive(message = "Id should be positive")
    @Hidden
    private Long id;

    @NotBlank(message = "Name should not be blank")
    @Size(max = 100, message = "Name should not be more than 100 symbols")
    @Schema(description = "Name")
    private String name;

    @Schema(description = "Description")
    private String description;

    @Positive(message = "Executor id should be positive")
    @Schema(description = "Employee executor unique identifier")
    private Long executorId;

    @NotNull(message = "Estimate hours should not be null")
    @Positive(message = "Estimate hours should be positive")
    @Schema(description = "Estimate hours")
    private Integer estimateHours;

    @NotNull(message = "Deadline should not be null")
    @Future(message = "Deadline should not be in past or now")
    @Schema(description = "Deadline date")
    private LocalDateTime deadlineDate;
}
