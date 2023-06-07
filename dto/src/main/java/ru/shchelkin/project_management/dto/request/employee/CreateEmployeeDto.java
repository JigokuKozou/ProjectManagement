package ru.shchelkin.project_management.dto.request.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEmployeeDto {
    @NotEmpty(message = "Surname should not be empty")
    @Size(max = 30, message = "Surname should not be more than 30 symbols")
    private String surname;

    @NotEmpty(message = "Name should not be empty")
    @Size(max = 20, message = "Name should not be more than 20 symbols")
    private String name;

    @Size(max = 30, message = "Patronymic should not be more than 30 symbols")
    private String patronymic;

    @Size(max = 100, message = "Job title should not be more than 20 symbols")
    private String jobTitle;

    @Email(message = "Email should be valid")
    @Size(max = 256, message = "Email should not be more than 256 symbols")
    private String email;

    @Size(max = 20, message = "Login should not be more than 20 symbols")
    private String login;

    @Size(max = 128, message = "Password should not be more than 128 symbols")
    private String password;
}
