package ru.shchelkin.project_management.dto.request.project;

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
public class CreateProjectDto {

    @NotBlank(message = "Code name should not be blank")
    @Size(max = 100, message = "Code name should not be more than 100 symbols")
    private String codeName;

    @NotBlank(message = "Name should not be blank")
    @Size(max = 100, message = "Name should not be more than 100 symbols")
    private String name;

    private String description;
}
