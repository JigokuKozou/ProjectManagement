package ru.shchelkin.project_management.commons.exceptions.employee;

import ru.shchelkin.project_management.commons.exceptions.NotFoundException;

public class EmployeeNotFoundException extends NotFoundException {
    public EmployeeNotFoundException() {
        super("Employee");
    }
}
