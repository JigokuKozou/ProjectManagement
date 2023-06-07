package ru.shchelkin.project_management.business.util.mapper;

import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.dto.request.employee.CreateEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.UpdateEmployeeDto;
import ru.shchelkin.project_management.model.Employee;

public class EmployeeDtoMapper {
    public static Employee toEmployee(UpdateEmployeeDto updateEmployeeDto) {
        var employee = toEmployee((CreateEmployeeDto) updateEmployeeDto);
        employee.setId(updateEmployeeDto.getId());
        employee.setStatus(updateEmployeeDto.getStatus());

        return employee;
    }

    public static Employee toEmployee(CreateEmployeeDto createEmployeeDto) {
        return Employee.builder()
                .surname(createEmployeeDto.getSurname())
                .name(createEmployeeDto.getName())
                .patronymic(createEmployeeDto.getPatronymic())
                .jobTitle(createEmployeeDto.getJobTitle())
                .email(createEmployeeDto.getEmail())
                .login(createEmployeeDto.getLogin())
                .password(createEmployeeDto.getPassword())
                .status(EmployeeStatus.ACTIVE)
                .build();
    }
}
