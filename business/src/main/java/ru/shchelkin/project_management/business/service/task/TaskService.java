package ru.shchelkin.project_management.business.service.task;

import ru.shchelkin.project_management.dto.request.task.ChangeTaskStatusDto;
import ru.shchelkin.project_management.dto.request.task.CreateTaskDto;
import ru.shchelkin.project_management.dto.request.task.SearchTaskDto;
import ru.shchelkin.project_management.dto.request.task.UpdateTaskDto;
import ru.shchelkin.project_management.dto.response.task.TaskCardDto;

import java.util.List;

public interface TaskService {
    TaskCardDto create(CreateTaskDto createTaskDto);

    TaskCardDto update(UpdateTaskDto updateTaskDto);

    List<TaskCardDto> getAll(SearchTaskDto searchTaskDto);

    void changeStatus(ChangeTaskStatusDto changeTaskStatusDto);
}
