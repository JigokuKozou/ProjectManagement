package ru.shchelkin.project_management.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shchelkin.project_management.dto.request.employee.GetEmployeeDto;

import java.util.Objects;

@Component
public class GetEmployeeDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return GetEmployeeDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GetEmployeeDto employeeDto = (GetEmployeeDto) target;

        if (Objects.isNull(employeeDto.getId()) && ObjectUtils.isEmpty(employeeDto.getLogin())) {
            errors.rejectValue("id", "", "id and login should not be both null");
            errors.rejectValue("login", "", "id and login should not be both null");
        }
    }
}
