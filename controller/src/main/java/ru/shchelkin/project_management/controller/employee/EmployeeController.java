package ru.shchelkin.project_management.controller.employee;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.shchelkin.project_management.business.service.employee.EmployeeService;
import ru.shchelkin.project_management.commons.role.TeamRole;
import ru.shchelkin.project_management.controller.config.SecurityConfig;
import ru.shchelkin.project_management.dto.request.employee.*;
import ru.shchelkin.project_management.dto.response.employee.EmployeeDto;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "EmployeeController", description = "Controller for employees")
@SecurityRequirement(name = SecurityConfig.SECURITY_CONFIG_NAME)
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Create new employee")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto create(@RequestBody @Valid CreateEmployeeDto employeeDto) {
        return employeeService.create(employeeDto);
    }

    @Operation(summary = "Get employee info")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto get(
            @Parameter(description = "Unique identifier")
            @RequestParam(required = false) Long id,
            @Parameter(description = "Unique login")
            @RequestParam(required = false) String login) {
        final GetEmployeeDto getEmployeeDto = new GetEmployeeDto(id, login);

        return employeeService.get(getEmployeeDto);
    }

    @Operation(summary = "Search for active employees")
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDto> getAll(
            @Parameter(description = "Surname (case-insensitive)")
                @RequestParam(name = "surname", required = false) String surname,
            @Parameter(description = "Name (case-insensitive)")
                @RequestParam(name = "name", required = false) String name,
            @Parameter(description = "Patronymic (case-insensitive)")
                @RequestParam(name = "patronymic", required = false) String patronymic,
            @Parameter(description = "Email (case-insensitive)")
                @RequestParam(name = "email", required = false) String email,
            @Parameter(description = "Unique login (case-insensitive)")
                @RequestParam(name = "login", required = false) String login) {
        final SearchEmployeeDto searchEmployeeDto = SearchEmployeeDto.builder()
                .surname(surname)
                .name(name)
                .patronymic(patronymic)
                .email(email)
                .login(login)
                .build();

        return employeeService.getAll(searchEmployeeDto);
    }

    @Operation(summary = "Search for active project employees")
    @GetMapping(value = "/search-by-project", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDto> getAll(
            @Parameter(description = "Unique project codename")
            @RequestParam(name = "projectCodename", required = false) String projectCodename,
            @Parameter(description = "Project team role")
            @RequestParam(name = "teamRole", required = false) TeamRole teamRole) {
        final FilterEmployeeByTeamRoleDto filterDao =
                new FilterEmployeeByTeamRoleDto(projectCodename, teamRole);

        return employeeService.getAll(filterDao);
    }

    @Operation(summary = "Update employee info")
    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto update(@RequestBody @Valid UpdateEmployeeDto employeeDto,
                              @Parameter(description = "Unique identifier") @PathVariable Long id) {
        employeeDto.setId(id);
        return employeeService.update(employeeDto);
    }

    @Operation(summary = "Delete employee")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "Unique identifier") @PathVariable Long id) {
        employeeService.delete(id);
    }
}
