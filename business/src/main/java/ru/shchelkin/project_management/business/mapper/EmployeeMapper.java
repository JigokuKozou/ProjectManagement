package ru.shchelkin.project_management.business.mapper;

import ru.shchelkin.project_management.dto.request.employee.CreateEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.UpdateEmployeeDto;
import ru.shchelkin.project_management.dto.response.employee.EmployeeCardDto;
import ru.shchelkin.project_management.model.Employee;

public class EmployeeMapper {
    public static EmployeeCardDto getEmployeeCardDto(Employee employee) {
        final EmployeeCardDto employeeCardDto = new EmployeeCardDto();

        map(employee, employeeCardDto);

        return employeeCardDto;
    }

    public static void map(CreateEmployeeDto from, Employee to) {
        to.setSurname(from.getSurname());
        to.setName(from.getName());
        to.setPatronymic(from.getPatronymic());
        to.setJobTitle(from.getJobTitle());
        to.setEmail(from.getEmail());
        to.setLogin(from.getLogin());
        to.setPassword(from.getPassword());
    }

    public static void map(UpdateEmployeeDto from, Employee to) {
        map((CreateEmployeeDto) from, to);

        to.setId(from.getId());
        to.setStatus(from.getStatus());
    }

    public static void map(Employee from, EmployeeCardDto to) {
        to.setId(from.getId());
        to.setSurname(from.getSurname());
        to.setName(from.getName());
        to.setPatronymic(from.getPatronymic());
        to.setJobTitle(from.getJobTitle());
        to.setEmail(from.getEmail());
        to.setStatus(from.getStatus());
        to.setLogin(from.getLogin());
        to.setPassword(from.getPassword());
    }
}
