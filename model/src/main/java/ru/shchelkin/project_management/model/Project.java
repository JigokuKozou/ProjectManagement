package ru.shchelkin.project_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shchelkin.project_management.commons.status.ProjectStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private Long id;

    private String codeName;

    private String name;

    private String description;

    private ProjectStatus status;
}
