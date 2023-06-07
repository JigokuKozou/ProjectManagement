package ru.shchelkin.project_management.business.service.employee.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shchelkin.project_management.business.service.employee.EmployeeService;
import ru.shchelkin.project_management.business.util.mapper.EmployeeDtoMapper;
import ru.shchelkin.project_management.business.util.validator.EmployeeDtoValidator;
import ru.shchelkin.project_management.dao.employee.EmployeeRepository;
import ru.shchelkin.project_management.dao.employee.specification.EmployeeSpecification;
import ru.shchelkin.project_management.dto.request.employee.*;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;
import ru.shchelkin.project_management.dto.response.employee.EmployeeCardDto;
import ru.shchelkin.project_management.model.Employee;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeJpaService implements EmployeeService {
    private final EmployeeRepository repository;

    @Autowired
    public EmployeeJpaService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmployeeCardDto create(CreateEmployeeDto createEmployeeDto) {
        Objects.requireNonNull(createEmployeeDto);
        EmployeeDtoValidator.requireValid(createEmployeeDto);

        Employee employee = EmployeeDtoMapper.toEmployee(createEmployeeDto);

        return new EmployeeCardDto(repository.save(employee));
    }

    @Override
    public List<EmployeeCardDto> getAll() {
        return repository.findAll()
                .stream()
                .map(EmployeeCardDto::new)
                .toList();
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
    public EmployeeCardDto getById(GetEmployeeByIdDto employeeByIdDto) {
        Employee employee = repository.findById(employeeByIdDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        return new EmployeeCardDto(employee);
    }

    @Override
    public EmployeeCardDto update(UpdateEmployeeDto updateEmployeeDto) {
        Objects.requireNonNull(updateEmployeeDto);
        EmployeeDtoValidator.requireValid(updateEmployeeDto);

        Employee employee = EmployeeDtoMapper.toEmployee(updateEmployeeDto);

        return new EmployeeCardDto(repository.save(employee));
    }

    @Override
    public void delete(DeleteEmployeeDto deleteEmployeeDto) {
        Objects.requireNonNull(deleteEmployeeDto);
        repository.deleteById(deleteEmployeeDto.getId());
    }
}
