package ru.shchelkin.project_management.dto.request.employee;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeDto extends CreateEmployeeDto {
    @Positive(message = "Id should be positive")
    private Long id;
}
