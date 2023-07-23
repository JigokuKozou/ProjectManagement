package ru.shchelkin.project_management.dao.employee;

import ru.shchelkin.project_management.dao.Dao;
import ru.shchelkin.project_management.dto.request.employee.FilterEmployeeByTeamRoleDto;
import ru.shchelkin.project_management.model.Employee;

import java.util.List;

public interface EmployeeDao extends Dao<Employee> {
    List<Employee> getAll(FilterEmployeeByTeamRoleDto filterDao);
}
