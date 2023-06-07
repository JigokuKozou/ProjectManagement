package ru.shchelkin.project_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;

@Data
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "status")
    private EmployeeStatus status;

    @Size(max = 20, message = "Login should not be more than 20 symbols")
    @Column(name = "login")
    private String login;

    @Size(max = 128, message = "Password should not be more than 128 symbols")
    @Column(name = "password")
    private String password;

    public void setSurname(String surname) {
        this.surname = surname.trim();
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic.trim();
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle.trim();
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public void setLogin(String login) {
        this.login = login.trim();
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }

    public static EmployeeBuilder builder() {
        return new EmployeeBuilder();
    }

    public static class EmployeeBuilder {
        private final Employee employee = new Employee();

        public EmployeeBuilder id(Long id) {
            employee.setId(id);
            return this;
        }

        public EmployeeBuilder surname(String surname) {
            employee.setSurname(surname);
            return this;
        }

        public EmployeeBuilder name(String name) {
            employee.setName(name);
            return this;
        }

        public EmployeeBuilder patronymic(String patronymic) {
            employee.setPatronymic(patronymic);
            return this;
        }

        public EmployeeBuilder jobTitle(String jobTitle) {
            employee.setJobTitle(jobTitle);
            return this;
        }

        public EmployeeBuilder email(String email) {
            employee.setEmail(email);
            return this;
        }

        public EmployeeBuilder status(EmployeeStatus status) {
            employee.setStatus(status);
            return this;
        }

        public EmployeeBuilder login(String login) {
            employee.setLogin(login);
            return this;
        }

        public EmployeeBuilder password(String password) {
            employee.setPassword(password);
            return this;
        }

        public Employee build() {
            return employee;
        }
    }
}
