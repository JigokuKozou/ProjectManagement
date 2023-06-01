package ru.shchelkin.project_management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    private Long id;

    private String surname;

    private String name;

    private String patronymic;

    private String jobTitle;

    private String email;

    private EmployeeStatus status;

    private String login;

    private String password;
}
