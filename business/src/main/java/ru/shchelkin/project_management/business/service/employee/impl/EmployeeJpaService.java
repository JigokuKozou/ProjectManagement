package ru.shchelkin.project_management.business.service.employee.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchelkin.project_management.business.mapper.EmployeeMapper;
import ru.shchelkin.project_management.business.service.employee.EmployeeService;
import ru.shchelkin.project_management.commons.exceptions.employee.EmployeeNotFoundException;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.dao.employee.EmployeeRepository;
import ru.shchelkin.project_management.dao.employee.specification.EmployeeSpecification;
import ru.shchelkin.project_management.dto.request.employee.CreateEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.GetEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.SearchEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.UpdateEmployeeDto;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;
import ru.shchelkin.project_management.dto.response.employee.EmployeeCardDto;
import ru.shchelkin.project_management.model.Employee;

import java.util.List;
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
    @Transactional
    public EmployeeCardDto create(@NonNull CreateEmployeeDto createEmployeeDto) {
        Employee employee = new Employee();
        EmployeeMapper.map(createEmployeeDto, employee);

        employee.setStatus(EmployeeStatus.ACTIVE);

        return EmployeeMapper.getEmployeeCardDto(repository.save(employee));
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeCardDto get(@NonNull GetEmployeeDto employeeDto) {
        Optional<Employee> employeeOptional = Optional.empty();

        if (Objects.nonNull(employeeDto.getId()))
            employeeOptional = repository.findById(employeeDto.getId());
        else if (Objects.nonNull(employeeDto.getLogin()))
            employeeOptional = repository.findByLogin(employeeDto.getLogin());

        Employee employee = employeeOptional.orElseThrow(EmployeeNotFoundException::new);

        return EmployeeMapper.getEmployeeCardDto(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeCardDto> getAll(@NonNull SearchEmployeeDto searchEmployeeDto) {
        return repository.findAll(EmployeeSpecification.get(searchEmployeeDto)).stream()
                .map(EmployeeMapper::getEmployeeCardDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeCardDto> getAll(@NonNull FilterEmployeeByTeamRoleDto filterDao) {
        return repository.findAll(EmployeeSpecification.get(filterDao)).stream()
                .map(EmployeeMapper::getEmployeeCardDto)
                .toList();
    }

    @Override
    @Transactional
    public EmployeeCardDto update(@NonNull UpdateEmployeeDto updateEmployeeDto) {
        Employee employee = repository.findById(updateEmployeeDto.getId())
                        .orElseThrow(EmployeeNotFoundException::new);

        EmployeeMapper.map(updateEmployeeDto, employee);

        return EmployeeMapper.getEmployeeCardDto(employee);
    }

    @Override
    @Transactional
    public void delete(@NonNull Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);

        employee.setStatus(EmployeeStatus.DELETED);
    }
}
