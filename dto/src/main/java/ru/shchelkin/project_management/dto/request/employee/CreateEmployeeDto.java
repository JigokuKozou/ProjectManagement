package ru.shchelkin.project_management.dto.request.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Employee info to create")
public class CreateEmployeeDto {

    @NotBlank(message = "Surname should not be blank")
    @Size(min = 1,max = 30, message = "Surname should not be more than 30 symbols")
    @Schema(description = "Surname")
    private String surname;

    @NotBlank(message = "Name should not be blank")
    @Size(min = 1, max = 20, message = "Name should not be more than 20 symbols")
    @Schema(description = "Name")
    private String name;

    @Size(max = 30, message = "Patronymic should not be more than 30 symbols")
    @Schema(description = "Patronymic")
    private String patronymic;

    @Size(max = 100, message = "Job title should not be more than 20 symbols")
    @Schema(description = "Job title")
    private String jobTitle;

    @Email(message = "Email should be valid")
    @Size(max = 256, message = "Email should not be more than 256 symbols")
    @Schema(description = "Email")
    private String email;

    @Size(max = 20, message = "Login should not be more than 20 symbols")
    @Schema(description = "Unique login")
    private String login;

    @Size(max = 128, message = "Password should not be more than 128 symbols")
    @Schema(description = "Password")
    private String password;
}
