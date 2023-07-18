package ru.shchelkin.project_management.controller.employee;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.shchelkin.project_management.business.service.employee.EmployeeService;
import ru.shchelkin.project_management.commons.role.TeamRole;
import ru.shchelkin.project_management.dto.request.employee.CreateEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.GetEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.SearchEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.UpdateEmployeeDto;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;
import ru.shchelkin.project_management.dto.response.employee.EmployeeDto;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController { // TODO: Done spring doc configuration (annotation, etc.)
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto create(@RequestBody @Valid CreateEmployeeDto employeeDto) {
        return employeeService.create(employeeDto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto get(@RequestParam(required = false) Long id,
                           @RequestParam(required = false) String login) {
        final GetEmployeeDto getEmployeeDto = GetEmployeeDto.builder()
                .id(id)
                .login(login)
                .build();

        return employeeService.get(getEmployeeDto);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDto> getAll(@RequestParam(name = "surname", required = false) String surname,
                                    @RequestParam(name = "name", required = false) String name,
                                    @RequestParam(name = "patronymic", required = false) String patronymic,
                                    @RequestParam(name = "email", required = false) String email,
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

    @GetMapping(value = "/search-by-project", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDto> getAll(@RequestParam(name = "projectCodeName", required = false) String projectCodeName,
                                    @RequestParam(name = "teamRole", required = false) TeamRole teamRole) {
        final FilterEmployeeByTeamRoleDto filterDao =
                new FilterEmployeeByTeamRoleDto(projectCodeName, teamRole);

        return employeeService.getAll(filterDao);
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto update(@RequestBody @Valid UpdateEmployeeDto employeeDto,
                              @PathVariable Long id) {
        employeeDto.setId(id);
        return employeeService.update(employeeDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
    }
}
