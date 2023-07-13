package ru.shchelkin.project_management.commons.exceptions.team_member;

import ru.shchelkin.project_management.commons.exceptions.WasNotRemovedException;

public class TeamMemberWasNotRemovedException extends WasNotRemovedException {
    public TeamMemberWasNotRemovedException() {
        super("Team member");
    }
}
