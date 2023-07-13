package ru.shchelkin.project_management.controller.task;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.shchelkin.project_management.business.service.task.TaskService;
import ru.shchelkin.project_management.dto.request.task.ChangeTaskStatusDto;
import ru.shchelkin.project_management.dto.request.task.CreateTaskDto;
import ru.shchelkin.project_management.dto.request.task.SearchTaskDto;
import ru.shchelkin.project_management.dto.request.task.UpdateTaskDto;
import ru.shchelkin.project_management.dto.response.task.TaskCardDto;

import java.util.List;

@RestController
@RequestMapping("api/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TaskCardDto create(@RequestBody @Valid CreateTaskDto createTaskDto) {
        return taskService.create(createTaskDto);
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskCardDto update(@RequestBody @Valid UpdateTaskDto updateTaskDto,
                              @PathVariable Long id) {
        updateTaskDto.setId(id);
        return taskService.update(updateTaskDto);
    }

    @GetMapping(value = "/search",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskCardDto> search(@RequestBody @Valid SearchTaskDto searchTaskDto) {
        return taskService.getAll(searchTaskDto);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeStatus(@RequestBody @Valid ChangeTaskStatusDto changeTaskStatusDto,
                             @PathVariable Long id) {
        changeTaskStatusDto.setId(id);
        taskService.changeStatus(changeTaskStatusDto);
    }
}
