package ru.shchelkin.project_management.commons.exceptions.project_team;

import ru.shchelkin.project_management.commons.exceptions.NotFoundException;

public class ProjectTeamNotFoundException extends NotFoundException {
    public ProjectTeamNotFoundException() {
        super("Project team");
    }
}
