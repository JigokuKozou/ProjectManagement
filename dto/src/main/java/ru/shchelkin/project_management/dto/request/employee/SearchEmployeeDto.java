package ru.shchelkin.project_management.dto.request.employee;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchEmployeeDto {
    @Size(max = 30, message = "Surname should not be more than 30 symbols")
    private String surname;

    @Size(max = 20, message = "Name should not be more than 20 symbols")
    private String name;

    @Size(max = 30, message = "Patronymic should not be more than 30 symbols")
    private String patronymic;

    @Size(max = 256, message = "Email should not be more than 256 symbols")
    private String email;

    @Size(max = 20, message = "Login should not be more than 20 symbols")
    private String login;
}
