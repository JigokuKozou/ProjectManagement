package ru.shchelkin.project_management.business.mapper;

import ru.shchelkin.project_management.dto.response.employee.EmployeeDto;
import ru.shchelkin.project_management.model.Employee;

public class EmployeeDtoMapper {
    public static EmployeeDto getEmployeeDto(Employee employee) {
        final EmployeeDto employeeDto = new EmployeeDto();

        map(employee, employeeDto);

        return employeeDto;
    }

    public static void map(Employee from, EmployeeDto to) {
        to.setId(from.getId());
        to.setSurname(from.getSurname());
        to.setName(from.getName());
        to.setPatronymic(from.getPatronymic());
        to.setJobTitle(from.getJobTitle());
        to.setEmail(from.getEmail());
        to.setStatus(from.getStatus());
        to.setLogin(from.getLogin());
    }
}
