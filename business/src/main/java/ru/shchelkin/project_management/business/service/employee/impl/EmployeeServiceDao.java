package ru.shchelkin.project_management.business.service.employee.impl;

import ru.shchelkin.project_management.business.mapper.EmployeeDtoMapper;
import ru.shchelkin.project_management.business.service.employee.EmployeeService;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.dao.employee.EmployeeDao;
import ru.shchelkin.project_management.dto.request.employee.CreateEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.GetEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.SearchEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.UpdateEmployeeDto;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;
import ru.shchelkin.project_management.dto.response.employee.EmployeeDto;
import ru.shchelkin.project_management.model.Employee;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class EmployeeServiceDao implements EmployeeService {
    private final EmployeeDao dao;

    public EmployeeServiceDao(EmployeeDao dao) {
        this.dao = dao;
    }

    @Override
    public EmployeeDto create(CreateEmployeeDto employeeDto) {
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

        return EmployeeDtoMapper.getEmployeeDto(employee);
    }

    @Override
    public EmployeeDto get(GetEmployeeDto employeeDto) {
        Optional<Employee> employeeOptional = Optional.empty();

        if (Objects.nonNull(employeeDto.getId()))
            employeeOptional = dao.getById(employeeDto.getId());
        else if (Objects.nonNull(employeeDto.getLogin()))
            employeeOptional = dao.getByLogin(employeeDto.getLogin());

        Employee employee = employeeOptional.orElseThrow(NoSuchElementException::new);

        return EmployeeDtoMapper.getEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAll(SearchEmployeeDto searchEmployeeDto) {
        throw new RuntimeException(); // TODO реализовать
    }

    @Override
    public List<EmployeeDto> getAll(FilterEmployeeByTeamRoleDto filterDao) {
        return dao.getAll(filterDao).stream().map(EmployeeDtoMapper::getEmployeeDto).toList();
    }

    @Override
    public EmployeeDto update(UpdateEmployeeDto employeeDto) {
        var employee = Employee.builder()
                .id(employeeDto.getId())
                .surname(employeeDto.getSurname())
                .name(employeeDto.getName())
                .patronymic(employeeDto.getPatronymic())
                .jobTitle(employeeDto.getJobTitle())
                .email(employeeDto.getEmail())
                .login(employeeDto.getLogin())
                .password(employeeDto.getPassword())
                .build();

        dao.update(employee);

        return EmployeeDtoMapper.getEmployeeDto(employee);
    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(id);
        dao.deleteById(id);
    }
}
