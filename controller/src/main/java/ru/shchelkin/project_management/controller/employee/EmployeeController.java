package ru.shchelkin.project_management.controller.employee;

import ru.shchelkin.project_management.business.service.employee.EmployeeService;
import ru.shchelkin.project_management.dto.request.employee.*;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;
import ru.shchelkin.project_management.dto.response.employee.EmployeeCardDto;

import java.util.List;

public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public EmployeeCardDto create(CreateEmployeeDto employeeDto) {
        return employeeService.create(employeeDto);
    }

    public List<EmployeeCardDto> getAll() {
        return employeeService.getAll();
    }

    public List<EmployeeCardDto> getAll(FilterEmployeeByTeamRoleDto filterDao) {
        return employeeService.getAll(filterDao);
    }

    public List<EmployeeCardDto> search(SearchEmployeeDto searchEmployeeDto) {
        return employeeService.getAll(searchEmployeeDto);
    }

    public EmployeeCardDto getById(GetEmployeeByIdDto employeeByIdDto) {
        return employeeService.getById(employeeByIdDto);
    }

    public EmployeeCardDto update(UpdateEmployeeDto employeeDto) {
        return employeeService.update(employeeDto);
    }

    public void delete(DeleteEmployeeDto deleteEmployeeDto) {
        employeeService.delete(deleteEmployeeDto);
    }
}
