package ru.shchelkin.project_management.commons.exceptions.team_member;

import ru.shchelkin.project_management.commons.exceptions.NotFoundException;

public class TeamMemberNotFoundException extends NotFoundException {
    public TeamMemberNotFoundException() {
        super("Team member");
    }
}
