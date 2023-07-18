package ru.shchelkin.project_management.dto.response.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Employee info")
public class EmployeeDto {

    @Schema(description = "Unique identifier")
    private Long id;

    @Schema(description = "Surname")
    private String surname;

    @Schema(description = "Name")
    private String name;

    @Schema(description = "Patronymic")
    private String patronymic;

    @Schema(description = "Job title")
    private String jobTitle;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "Status")
    private EmployeeStatus status;

    @Schema(description = "Unique login")
    private String login;

    @Schema(description = "Password")
    private String password;
}
