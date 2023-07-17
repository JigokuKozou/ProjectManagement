package ru.shchelkin.project_management.dto.request.employee;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetEmployeeDto {
    @Positive(message = "Id should be positive")
    private Long id;

    @Size(max = 20, message = "Login should not be more than 20 symbols")
    private String login;
}
