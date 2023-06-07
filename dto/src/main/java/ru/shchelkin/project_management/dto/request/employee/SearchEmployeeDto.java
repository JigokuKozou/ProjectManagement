package ru.shchelkin.project_management.dto.request.employee;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchEmployeeDto {
    private String surname;

    private String name;

    private String patronymic;

    private String login;

    private String email;
}
