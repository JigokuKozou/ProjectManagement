package ru.shchelkin.project_management.dto.request.employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEmployeeDto {
    private String surname;

    private String name;

    private String patronymic;

    private String jobTitle;

    private String email;

    private String login;

    private String password;
}
