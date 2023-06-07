package ru.shchelkin.project_management.business.util.validator;

import org.springframework.util.ObjectUtils;
import ru.shchelkin.project_management.dto.request.employee.CreateEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.UpdateEmployeeDto;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDtoValidator {
    public static void requireValid(UpdateEmployeeDto updateEmployeeDto) {
        List<String> emptyFieldNames = new ArrayList<>();

        addEmptyField(emptyFieldNames, updateEmployeeDto);

        if (!emptyFieldNames.isEmpty())
            throw new IllegalArgumentException(
                    String.join(", ", emptyFieldNames) + " should not be empty");
    }
    public static void requireValid(CreateEmployeeDto createEmployeeDto) {
        List<String> emptyFieldNames = new ArrayList<>();

        addEmptyField(emptyFieldNames, createEmployeeDto);

        if (!emptyFieldNames.isEmpty())
            throw new IllegalArgumentException(
                    String.join(", ", emptyFieldNames) + " should not be empty");
    }

    private static void addEmptyField(List<String> emptyFieldNames, UpdateEmployeeDto updateEmployeeDto) {
        if (ObjectUtils.isEmpty(updateEmployeeDto.getId()))
            emptyFieldNames.add("id");
        if (ObjectUtils.isEmpty(updateEmployeeDto.getStatus()))
            emptyFieldNames.add("status");

        addEmptyField(emptyFieldNames, (CreateEmployeeDto) updateEmployeeDto);
    }

    private static void addEmptyField(List<String> emptyFieldNames, CreateEmployeeDto createEmployeeDto) {
        if (ObjectUtils.isEmpty(createEmployeeDto.getSurname()))
            emptyFieldNames.add("surname");
        if (ObjectUtils.isEmpty(createEmployeeDto.getName()))
            emptyFieldNames.add("name");
    }
}
