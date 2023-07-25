package ru.shchelkin.project_management.business.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.shchelkin.project_management.business.service.security.JwtService;
import ru.shchelkin.project_management.commons.exceptions.employee.EmployeeNotFoundException;
import ru.shchelkin.project_management.dao.employee.EmployeeRepository;
import ru.shchelkin.project_management.dto.request.auth.AuthenticationRequestDto;
import ru.shchelkin.project_management.dto.response.auth.AuthenticationResponseDto;
import ru.shchelkin.project_management.model.Employee;

import java.util.Optional;

@Slf4j
@Service
public class AuthenticationService {

    private final JwtService jwtService;

    private final EmployeeRepository employeeRepository;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(JwtService jwtService, EmployeeRepository employeeRepository, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.employeeRepository = employeeRepository;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getLogin(),
                        authRequest.getPassword()
                )
        );

        final Optional<Employee> user = employeeRepository.findByLogin(authRequest.getLogin());

        if (user.isEmpty()) {
            log.warn("Employee with login:\"%s\" not found.".formatted(authRequest.getLogin()));

            throw new EmployeeNotFoundException();
        }

        final String jwtToken = jwtService.generateToken(user.get().getLogin());

        log.info("Token was issued to employee with id:%d.".formatted(user.get().getId()));

        return new AuthenticationResponseDto(jwtToken);
    }
}
