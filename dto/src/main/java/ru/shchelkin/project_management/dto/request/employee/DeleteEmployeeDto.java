package ru.shchelkin.project_management.dto.request.employee;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteEmployeeDto {
    @NotNull(message = "Id should not be null")
    @Positive(message = "Id should be positive")
    private Long id;
}
