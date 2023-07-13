package ru.shchelkin.project_management.commons.exceptions.project;

import ru.shchelkin.project_management.commons.exceptions.NotFoundException;

public class ProjectNotFoundException extends NotFoundException {
    public ProjectNotFoundException() {
        super("Project");
    }
}
