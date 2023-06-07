package ru.shchelkin.project_management.dto.request.employee;

import lombok.Getter;
import lombok.Setter;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;

@Getter
@Setter
public class UpdateEmployeeDto extends CreateEmployeeDto {
    private Long id;

    private EmployeeStatus status;
}
