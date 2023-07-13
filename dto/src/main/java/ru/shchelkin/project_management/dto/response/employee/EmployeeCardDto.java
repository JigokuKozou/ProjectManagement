package ru.shchelkin.project_management.dto.response.employee;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;

@Getter
@Setter
@ToString
public class EmployeeCardDto {
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
