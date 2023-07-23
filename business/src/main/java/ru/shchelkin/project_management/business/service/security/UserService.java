package ru.shchelkin.project_management.business.service.security;

import jakarta.annotation.PostConstruct;
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
        Employee employee = employeeRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return new AccountDetails(employee, Collections.emptyList());
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
