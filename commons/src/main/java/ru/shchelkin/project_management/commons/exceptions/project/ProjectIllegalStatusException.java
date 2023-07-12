package ru.shchelkin.project_management.commons.exceptions.project;

import ru.shchelkin.project_management.commons.exceptions.IllegalStatusException;
import ru.shchelkin.project_management.commons.status.ProjectStatus;

public class ProjectIllegalStatusException extends IllegalStatusException {
    public ProjectIllegalStatusException(ProjectStatus current, ProjectStatus value) {
        super("Project", current.toString(), value.toString());
    }
}
