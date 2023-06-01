package ru.shchelkin.project_management.dto.response.employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.model.Employee;

@Getter
@Setter
@NoArgsConstructor
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

    public EmployeeCardDto(Employee employee) {
        id = employee.getId();
        surname = employee.getSurname();
        name = employee.getName();
        patronymic = employee.getPatronymic();
        jobTitle = employee.getJobTitle();
        email = employee.getEmail();
        status = employee.getStatus();
        login = employee.getLogin();
        password = employee.getPassword();
    }
}
