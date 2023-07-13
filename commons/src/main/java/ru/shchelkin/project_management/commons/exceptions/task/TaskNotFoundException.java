package ru.shchelkin.project_management.commons.exceptions.task;

import ru.shchelkin.project_management.commons.exceptions.NotFoundException;

public class TaskNotFoundException extends NotFoundException {
    public TaskNotFoundException() {
        super("Task");
    }
}
