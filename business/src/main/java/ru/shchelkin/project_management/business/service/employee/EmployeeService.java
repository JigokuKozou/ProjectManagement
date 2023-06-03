package ru.shchelkin.project_management.business.service.employee;

import ru.shchelkin.project_management.dto.request.employee.*;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;
import ru.shchelkin.project_management.dto.response.employee.EmployeeCardDto;

import java.util.List;

public interface EmployeeService {
    EmployeeCardDto create(CreateEmployeeDto employeeDto);

    List<EmployeeCardDto> getAll();

    List<EmployeeCardDto> getAll(SearchEmployeeDto searchEmployeeDto);

    List<EmployeeCardDto> getAll(FilterEmployeeByTeamRoleDto filterDao);

    EmployeeCardDto getById(GetEmployeeByIdDto employeeByIdDto);

    EmployeeCardDto update(UpdateEmployeeDto employeeDto);

    void delete(DeleteEmployeeDto deleteEmployeeDto);
}
