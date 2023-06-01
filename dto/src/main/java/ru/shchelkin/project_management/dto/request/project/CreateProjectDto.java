package ru.shchelkin.project_management.dto.request.project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectDto {
    private String codeName;

    private String name;

    private String description;
}
