package ru.shchelkin.project_management.business.service.employee;

import ru.shchelkin.project_management.dto.request.employee.CreateEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.GetEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.SearchEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.UpdateEmployeeDto;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;
import ru.shchelkin.project_management.dto.response.employee.EmployeeCardDto;

import java.util.List;

public interface EmployeeService {
    EmployeeCardDto create(CreateEmployeeDto employeeDto);

    EmployeeCardDto get(GetEmployeeDto getEmployeeDto);

    List<EmployeeCardDto> getAll(SearchEmployeeDto searchEmployeeDto);

    List<EmployeeCardDto> getAll(FilterEmployeeByTeamRoleDto filterDao);

    EmployeeCardDto update(UpdateEmployeeDto employeeDto);

    void delete(Long id);
}
