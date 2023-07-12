package ru.shchelkin.project_management.commons.exceptions.task;

import ru.shchelkin.project_management.commons.exceptions.IllegalStatusException;
import ru.shchelkin.project_management.commons.status.TaskStatus;

public class TaskIllegalStatusException extends IllegalStatusException {
    public TaskIllegalStatusException(TaskStatus current, TaskStatus value) {
        super("Task", current.toString(), value.toString());
    }
}
