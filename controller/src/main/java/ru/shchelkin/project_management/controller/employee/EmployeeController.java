package ru.shchelkin.project_management.controller.employee;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shchelkin.project_management.business.service.employee.EmployeeService;
import ru.shchelkin.project_management.commons.exceptions.NotValidException;
import ru.shchelkin.project_management.controller.validator.GetEmployeeDtoValidator;
import ru.shchelkin.project_management.dto.request.employee.CreateEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.GetEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.SearchEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.UpdateEmployeeDto;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;
import ru.shchelkin.project_management.dto.response.employee.EmployeeCardDto;

import java.util.List;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    private final GetEmployeeDtoValidator getEmployeeDtoValidator;

    @Autowired
    public EmployeeController(EmployeeService employeeService,
                              GetEmployeeDtoValidator getEmployeeDtoValidator) {
        this.employeeService = employeeService;
        this.getEmployeeDtoValidator = getEmployeeDtoValidator;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeCardDto create(@RequestBody CreateEmployeeDto employeeDto) {
        return employeeService.create(employeeDto);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeCardDto get(@RequestBody @Valid GetEmployeeDto getEmployeeDto,
                               BindingResult bindingResult) {
        getEmployeeDtoValidator.validate(getEmployeeDto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new NotValidException(bindingResult);
        }

        return employeeService.get(getEmployeeDto);
    }

    @GetMapping(value = "/search",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeCardDto> getAll(@RequestBody @Valid SearchEmployeeDto searchEmployeeDto) {
        return employeeService.getAll(searchEmployeeDto);
    }

    @GetMapping(value = "/search-by-project",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeCardDto> getAll(@RequestBody @Valid FilterEmployeeByTeamRoleDto filterDao) {
        return employeeService.getAll(filterDao);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeCardDto update(@RequestBody @Valid UpdateEmployeeDto employeeDto) {
        return employeeService.update(employeeDto);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
    }
}
