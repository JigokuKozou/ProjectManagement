package ru.shchelkin.project_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "Id should be positive")
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Surname should not be blank")
    @Size(max = 30, message = "Surname should not be more than 30 symbols")
    @Column(name = "surname")
    private String surname;

    @NotBlank(message = "Name should not be blank")
    @Size(max = 20, message = "Name should not be more than 20 symbols")
    @Column(name = "name")
    private String name;

    @Size(max = 30, message = "Surname should not be more than 30 symbols")
    @Column(name = "patronymic")
    private String patronymic;

    @Size(max = 100, message = "Job title should not be more than 20 symbols")
    @Column(name = "job_title")
    private String jobTitle;

    @Email(message = "Email should be valid")
    @Size(max = 256, message = "Email should not be more than 256 symbols")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Status should not be null")
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private EmployeeStatus status;

    @Size(max = 20, message = "Login should not be more than 20 symbols")
    @Column(name = "login")
    private String login;

    @Size(max = 128, message = "Password should not be more than 128 symbols")
    @Column(name = "password")
    private String password;
}
