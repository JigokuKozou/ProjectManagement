package ru.shchelkin.project_management.business.service.employee.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shchelkin.project_management.business.service.employee.EmployeeService;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.dao.employee.EmployeeRepository;
import ru.shchelkin.project_management.dao.employee.specification.EmployeeSpecification;
import ru.shchelkin.project_management.dto.request.employee.*;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;
import ru.shchelkin.project_management.dto.response.employee.EmployeeCardDto;
import ru.shchelkin.project_management.model.Employee;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeJpaService implements EmployeeService {
    private final EmployeeRepository repository;

    @Autowired
    public EmployeeJpaService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmployeeCardDto create(CreateEmployeeDto createEmployeeDto) {
        Employee employee = createEmployeeDto.toEmployee();
        employee.setStatus(EmployeeStatus.ACTIVE);

        return new EmployeeCardDto(repository.save(employee));
    }

    @Override
    public EmployeeCardDto get(GetEmployeeDto employeeDto) {
        Optional<Employee> employeeOptional = Optional.empty();

        if (Objects.nonNull(employeeDto.getId()))
            employeeOptional = repository.findById(employeeDto.getId());
        else if (Objects.nonNull(employeeDto.getLogin()))
            employeeOptional = repository.findByLogin(employeeDto.getLogin());

        Employee employee = employeeOptional.orElseThrow(NoSuchElementException::new);

        return new EmployeeCardDto(employee);
    }

    @Override
    public List<EmployeeCardDto> getAll(SearchEmployeeDto searchEmployeeDto) {
        return repository.findAll(EmployeeSpecification.get(searchEmployeeDto)).stream()
                .map(EmployeeCardDto::new)
                .toList();
    }

    @Override
    public List<EmployeeCardDto> getAll(FilterEmployeeByTeamRoleDto filterDao) {
        return repository.findAll(EmployeeSpecification.get(filterDao)).stream()
                .map(EmployeeCardDto::new)
                .toList();
    }

    @Override
    public EmployeeCardDto update(UpdateEmployeeDto updateEmployeeDto) {
        Employee employee = updateEmployeeDto.toEmployee();

        return new EmployeeCardDto(repository.save(employee));
    }

    @Override
    public void delete(DeleteEmployeeDto deleteEmployeeDto) {
        repository.deleteById(deleteEmployeeDto.getId());
    }
}
