package ru.shchelkin.project_management.business.service.employee.impl;

import ru.shchelkin.project_management.business.service.employee.EmployeeService;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.dao.Dao;
import ru.shchelkin.project_management.dto.request.employee.*;
import ru.shchelkin.project_management.dto.response.employee.EmployeeCardDto;
import ru.shchelkin.project_management.model.Employee;

import java.util.List;
import java.util.NoSuchElementException;

public class EmployeeServiceDao implements EmployeeService {
    private final Dao<Employee> dao;

    public EmployeeServiceDao(Dao<Employee> dao) {
        this.dao = dao;
    }

    @Override
    public EmployeeCardDto create(CreateEmployeeDto employeeDto) {
        var employee = Employee.builder()
                .surname(employeeDto.getSurname().trim())
                .name(employeeDto.getName().trim())
                .patronymic(employeeDto.getPatronymic().trim())
                .jobTitle(employeeDto.getJobTitle().trim())
                .email(employeeDto.getEmail().trim())
                .status(EmployeeStatus.ACTIVE)
                .login(employeeDto.getLogin().trim())
                .password(employeeDto.getPassword().trim())
                .build();

        dao.create(employee);

        return new EmployeeCardDto(employee);
    }

    @Override
    public List<EmployeeCardDto> getAll() {
        return dao.getAll().stream().map(EmployeeCardDto::new).toList();
    }

    @Override
    public List<EmployeeCardDto> getAll(SearchEmployeeDto searchEmployeeDto) {
        throw new RuntimeException(); // TODO реализовать
    }

    @Override
    public EmployeeCardDto getById(GetEmployeeByIdDto employeeByIdDto) {
        Employee employee = dao.getById(employeeByIdDto.getId()).orElseThrow(NoSuchElementException::new);

        return new EmployeeCardDto(employee);
    }

    @Override
    public EmployeeCardDto update(UpdateEmployeeDto employeeDto) {
        var employee = Employee.builder()
                .id(employeeDto.getId())
                .surname(employeeDto.getSurname())
                .name(employeeDto.getName())
                .patronymic(employeeDto.getPatronymic())
                .jobTitle(employeeDto.getJobTitle())
                .email(employeeDto.getEmail())
                .status(employeeDto.getStatus())
                .login(employeeDto.getLogin())
                .password(employeeDto.getPassword())
                .build();

        dao.update(employee);

        return new EmployeeCardDto(employee);
    }

    @Override
    public void delete(DeleteEmployeeDto deleteEmployeeDto) {
        dao.deleteById(deleteEmployeeDto.getId());
    }
}
