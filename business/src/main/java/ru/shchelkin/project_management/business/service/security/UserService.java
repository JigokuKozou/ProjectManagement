package ru.shchelkin.project_management.business.service.security;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.dao.employee.EmployeeRepository;
import ru.shchelkin.project_management.model.Employee;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Value("${security.admin.login:admin}")
    private String adminLogin;

    @Value("${security.admin.password}")
    private String adminPassword;

    @Value("${security.admin.surname:Admin}")
    private String adminSurname;

    @Value("${security.admin.name:Admin}")
    private String adminName;

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByLogin(username);

        if (employee.isEmpty()) {
            final String errorMessage = "Employee with login:\"%s\" not found.".formatted(username);

            log.warn(errorMessage);

            throw new UsernameNotFoundException(errorMessage);
        }

        log.info("Employee with login: \"%s\" founded.".formatted(username));

        return new AccountDetails(employee.get(), Collections.emptyList());
    }

    @PostConstruct
    public void initAdmin() {
        employeeRepository.findByLogin(adminLogin)
                .orElseGet(() -> employeeRepository
                        .saveAndFlush(Employee.builder()
                                .login(adminLogin)
                                .password(passwordEncoder.encode(adminPassword))
                                .surname(adminSurname)
                                .name(adminName)
                                .status(EmployeeStatus.ACTIVE)
                                .build()
                        )
                );
    }
}
