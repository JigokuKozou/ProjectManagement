package ru.shchelkin.project_management.business.service.task;

import ru.shchelkin.project_management.dto.request.task.ChangeTaskStatusDto;
import ru.shchelkin.project_management.dto.request.task.CreateTaskDto;
import ru.shchelkin.project_management.dto.request.task.SearchTaskDto;
import ru.shchelkin.project_management.dto.request.task.UpdateTaskDto;
import ru.shchelkin.project_management.dto.response.task.TaskDto;

import java.util.List;

public interface TaskService {
    TaskDto create(CreateTaskDto createTaskDto);

    TaskDto update(UpdateTaskDto updateTaskDto);

    List<TaskDto> getAll(SearchTaskDto searchTaskDto);

    void changeStatus(ChangeTaskStatusDto changeTaskStatusDto);
}
