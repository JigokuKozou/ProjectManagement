package ru.shchelkin.project_management.controller.employee;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
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

    @PostMapping
    public EmployeeCardDto create(@RequestBody @Valid CreateEmployeeDto employeeDto) {
        return employeeService.create(employeeDto);
    }

    @GetMapping
    public EmployeeCardDto getAll(@RequestBody @Valid GetEmployeeDto getEmployeeDto,
                                  BindingResult bindingResult) throws NoSuchMethodException, MethodArgumentNotValidException {
        getEmployeeDtoValidator.validate(getEmployeeDto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(
                    new MethodParameter(this.getClass().getMethod("getAll", GetEmployeeDto.class, BindingResult.class), 0),
                    bindingResult);
        }

        return employeeService.get(getEmployeeDto);
    }

    @GetMapping("/search")
    public List<EmployeeCardDto> getAll(@RequestBody @Valid SearchEmployeeDto searchEmployeeDto) {
        return employeeService.getAll(searchEmployeeDto);
    }

    @GetMapping("/search-by-project")
    public List<EmployeeCardDto> getAll(@RequestBody @Valid FilterEmployeeByTeamRoleDto filterDao) {
        return employeeService.getAll(filterDao);
    }

    @PutMapping
    public EmployeeCardDto update(@RequestBody @Valid PutEmployeeDto employeeDto) {
        return employeeService.update(employeeDto);
    }

    @DeleteMapping
    public void delete(@RequestBody @Valid DeleteEmployeeDto deleteEmployeeDto) {
        employeeService.delete(deleteEmployeeDto);
    }
}
