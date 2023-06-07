package ru.shchelkin.project_management.controller.employee;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.shchelkin.project_management.business.service.employee.EmployeeService;
import ru.shchelkin.project_management.controller.validator.GetEmployeeDtoValidator;
import ru.shchelkin.project_management.dto.request.employee.*;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;
import ru.shchelkin.project_management.dto.response.employee.EmployeeCardDto;

import java.util.List;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    private final GetEmployeeDtoValidator getEmployeeDtoValidator;

    @Autowired
    public EmployeeController(EmployeeService employeeService, GetEmployeeDtoValidator getEmployeeDtoValidator) {
        this.employeeService = employeeService;
        this.getEmployeeDtoValidator = getEmployeeDtoValidator;
    }

    @PostMapping(value = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeCardDto create(@RequestBody @Valid CreateEmployeeDto employeeDto) {
        return employeeService.create(employeeDto);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeCardDto get(@RequestBody @Valid GetEmployeeDto getEmployeeDto,
                                  BindingResult bindingResult) throws NoSuchMethodException, MethodArgumentNotValidException {
        getEmployeeDtoValidator.validate(getEmployeeDto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(
                    new MethodParameter(this.getClass().getMethod("get", GetEmployeeDto.class, BindingResult.class), 0),
                    bindingResult);
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

    @PostMapping(value = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeCardDto update(@RequestBody @Valid UpdateEmployeeDto employeeDto) {
        return employeeService.update(employeeDto);
    }

    @PostMapping(value = "/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> delete(@RequestBody @Valid DeleteEmployeeDto deleteEmployeeDto) {
        employeeService.delete(deleteEmployeeDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
