package ru.shchelkin.project_management.dto.request.employee;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.model.Employee;

@Getter
@Setter
public class UpdateEmployeeDto extends CreateEmployeeDto {
    @NotNull(message = "Id should not be null")
    @Positive(message = "Id should be positive")
    private Long id;

    @NotNull(message = "Status should not be null")
    private EmployeeStatus status;

    @Override
    public Employee toEmployee() {
        var employee = super.toEmployee();
        employee.setId(id);
        employee.setStatus(status);

        return employee;
    }
}
