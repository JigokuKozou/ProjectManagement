package ru.shchelkin.project_management.business.service.employee;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.shchelkin.project_management.business.mapper.EmployeeDtoMapper;
import ru.shchelkin.project_management.commons.exceptions.*;
import ru.shchelkin.project_management.commons.exceptions.employee.EmployeeIllegalStateException;
import ru.shchelkin.project_management.commons.exceptions.employee.EmployeeNotFoundException;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.dao.employee.EmployeeRepository;
import ru.shchelkin.project_management.dao.employee.specification.EmployeeSpecification;
import ru.shchelkin.project_management.dto.request.employee.*;
import ru.shchelkin.project_management.dto.response.employee.EmployeeDto;
import ru.shchelkin.project_management.model.Employee;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.shchelkin.project_management.commons.util.CustomStringUtils.strip;

@Slf4j
@Service
public class EmployeeJpaService implements EmployeeService {

    public static final int SURNAME_MAX_LENGTH = 30;
    public static final int NAME_MAX_LENGTH = 20;
    public static final int PATRONYMIC_MAX_LENGTH = 30;
    public static final int TITLE_MAX_LENGTH_JOB = 100;
    public static final int EMAIL_MAX_LENGTH = 256;
    public static final int LOGIN_MAX_LENGTH = 20;
    public static final int PASSWORD_MAX_LENGTH = 128;

    private final EmployeeRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeJpaService(EmployeeRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public EmployeeDto create(@NonNull CreateEmployeeDto createEmployeeDto) {
        validateSurname(createEmployeeDto.getSurname());
        validateName(createEmployeeDto.getName());
        validatePatronymic(createEmployeeDto.getPatronymic());
        validateJobTitle(createEmployeeDto.getJobTitle());
        validateEmail(createEmployeeDto.getEmail());
        validateLoginAndPassword(createEmployeeDto.getLogin(), createEmployeeDto.getPassword(), null);

        Employee employee = new Employee();
        map(createEmployeeDto, employee);

        employee.setStatus(EmployeeStatus.ACTIVE);

        employee = repository.saveAndFlush(employee);

        log.info("Created new employee with id:" + employee.getId());

        return EmployeeDtoMapper.getEmployeeDto(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto get(@NonNull GetEmployeeDto getEmployeeDto) {
        Optional<Employee> employeeOptional;

        if (Objects.nonNull(getEmployeeDto.getId()))
            employeeOptional = repository.findById(getEmployeeDto.getId());
        else if (StringUtils.hasText(getEmployeeDto.getLogin()))
            employeeOptional = repository.findByLogin(getEmployeeDto.getLogin());
        else
            throw new ArgumentsException()
                    .add("id", "Id and login should not be both null.")
                    .add("login", "Id and login should not be both null.");

        Employee employee = employeeOptional.orElseThrow(EmployeeNotFoundException::new);

        return EmployeeDtoMapper.getEmployeeDto(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> getAll(@NonNull SearchEmployeeDto searchEmployeeDto) {
        return repository.findAll(EmployeeSpecification.get(searchEmployeeDto)).stream()
                .map(EmployeeDtoMapper::getEmployeeDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> getAll(@NonNull FilterEmployeeByTeamRoleDto filterDao) {
        return repository.findAll(EmployeeSpecification.get(filterDao)).stream()
                .map(EmployeeDtoMapper::getEmployeeDto)
                .toList();
    }

    @Override
    @Transactional
    public EmployeeDto update(@NonNull UpdateEmployeeDto updateEmployeeDto) {
        validateId(updateEmployeeDto.getId());
        validateSurname(updateEmployeeDto.getSurname());
        validateName(updateEmployeeDto.getName());
        validatePatronymic(updateEmployeeDto.getPatronymic());
        validateJobTitle(updateEmployeeDto.getJobTitle());
        validateEmail(updateEmployeeDto.getEmail());

        Employee employee = repository.findById(updateEmployeeDto.getId())
                        .orElseThrow(EmployeeNotFoundException::new);

        validateLoginAndPassword(updateEmployeeDto.getLogin(), updateEmployeeDto.getPassword(),
                employee.getId());

        if (employee.getStatus() == EmployeeStatus.DELETED) {
            String errorMessage = "Employee with id:%d has been deleted and cannot be updated."
                    .formatted(updateEmployeeDto.getId());
            log.warn(errorMessage);
            throw new EmployeeIllegalStateException(errorMessage);
        }

        map(updateEmployeeDto, employee);

        log.info("Updated employee with id:" + employee.getId());

        return EmployeeDtoMapper.getEmployeeDto(employee);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validateId(id);

        Employee employee = repository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);

        if (employee.getStatus() == EmployeeStatus.DELETED) {
            String errorMessage = "Employee with id:%d was already deleted.".formatted(id);
            log.warn(errorMessage);
            throw new EmployeeIllegalStateException(errorMessage);
        }

        employee.setStatus(EmployeeStatus.DELETED);

        log.info("Deleted employee with id:" + employee.getId());
    }

    private void map(CreateEmployeeDto from, Employee to) {
        to.setSurname(from.getSurname().strip());
        to.setName(from.getName().strip());
        to.setPatronymic(strip(from.getPatronymic()));
        to.setJobTitle(strip(from.getJobTitle()));
        to.setEmail(strip(from.getEmail()));

        final String login = strip(from.getLogin());
        final String password = strip(from.getPassword());
        if (!login.isEmpty()) {
            to.setLogin(login);

            if (Objects.nonNull(password))
                to.setPassword(passwordEncoder.encode(password));
        }
    }

    private void validateId(Long id) {
        if (Objects.isNull(id)) throw new NullException("id");
    }

    private void validateSurname(String surname) {
        if (!StringUtils.hasText(surname))
            throw new BlankException("surname");
        if (surname.length() > SURNAME_MAX_LENGTH)
            throw new MaxLengthException("surname", SURNAME_MAX_LENGTH);
    }

    private void validateName(String name) {
        if (!StringUtils.hasText(name))
            throw new BlankException("name");
        if (name.length() > NAME_MAX_LENGTH)
            throw new MaxLengthException("name", NAME_MAX_LENGTH);
    }

    private void validatePatronymic(String patronymic) {
        if (!StringUtils.hasText(patronymic))
            return;
        if (patronymic.strip().length() > PATRONYMIC_MAX_LENGTH)
            throw new MaxLengthException("patronymic", PATRONYMIC_MAX_LENGTH);
    }

    private void validateJobTitle(String jobTitle) {
        if (!StringUtils.hasText(jobTitle))
            return;
        if (jobTitle.strip().length() > TITLE_MAX_LENGTH_JOB)
            throw new MaxLengthException("jobTitle", TITLE_MAX_LENGTH_JOB, "Job title");
    }

    private void validateEmail(String email) {
        if (!StringUtils.hasText(email))
            return;
        if (email.strip().length() > EMAIL_MAX_LENGTH)
            throw new MaxLengthException("email", EMAIL_MAX_LENGTH);
    }

    private void validateLoginAndPassword(String login, String password, @Nullable Long id) {
        if (!StringUtils.hasText(login))
            return;

        login = login.strip();

        if (repository.findByLogin(login)
                .filter(another -> !Objects.equals(another.getId(), id))
                .isPresent())
            throw new NotUniqueException("login");
        if (login.length() > LOGIN_MAX_LENGTH)
            throw new MaxLengthException("login", LOGIN_MAX_LENGTH);

        if (!StringUtils.hasText(password))
            throw new BlankException("password");
        else if (password.strip().length() > PASSWORD_MAX_LENGTH)
            throw new MaxLengthException("password", PASSWORD_MAX_LENGTH);
    }
}
