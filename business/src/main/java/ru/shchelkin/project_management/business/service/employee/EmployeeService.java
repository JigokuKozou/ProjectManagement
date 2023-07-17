package ru.shchelkin.project_management.business.service.employee;

import ru.shchelkin.project_management.dto.request.employee.CreateEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.GetEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.SearchEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.UpdateEmployeeDto;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;
import ru.shchelkin.project_management.dto.response.employee.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto create(CreateEmployeeDto employeeDto);

    EmployeeDto get(GetEmployeeDto getEmployeeDto);

    List<EmployeeDto> getAll(SearchEmployeeDto searchEmployeeDto);

    List<EmployeeDto> getAll(FilterEmployeeByTeamRoleDto filterDao);

    EmployeeDto update(UpdateEmployeeDto employeeDto);

    void delete(Long id);
}
