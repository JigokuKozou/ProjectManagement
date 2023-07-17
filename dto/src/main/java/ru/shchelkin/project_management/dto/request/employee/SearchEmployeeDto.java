package ru.shchelkin.project_management.dto.request.employee;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchEmployeeDto {
    private String surname;

    private String name;

    private String patronymic;

    private String email;

    private String login;
}
