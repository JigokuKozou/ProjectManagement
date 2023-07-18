package ru.shchelkin.project_management.controller.task;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.shchelkin.project_management.business.service.task.TaskService;
import ru.shchelkin.project_management.commons.status.TaskStatus;
import ru.shchelkin.project_management.dto.request.task.ChangeTaskStatusDto;
import ru.shchelkin.project_management.dto.request.task.CreateTaskDto;
import ru.shchelkin.project_management.dto.request.task.SearchTaskDto;
import ru.shchelkin.project_management.dto.request.task.UpdateTaskDto;
import ru.shchelkin.project_management.dto.response.task.TaskDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto create(@RequestBody @Valid CreateTaskDto createTaskDto) {
        return taskService.create(createTaskDto);
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskDto update(@RequestBody @Valid UpdateTaskDto updateTaskDto,
                          @PathVariable Long id) {
        updateTaskDto.setId(id);
        return taskService.update(updateTaskDto);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDto> search(@RequestParam(required = false) String name,
                                @RequestParam(required = false) List<TaskStatus> statuses,
                                @RequestParam(required = false) Long executorId,
                                @RequestParam(required = false) Long authorId,
                                @RequestParam(required = false) LocalDateTime deadlineTimeStart,
                                @RequestParam(required = false) LocalDateTime deadlineTimeEnd,
                                @RequestParam(required = false) LocalDateTime startTaskTimeStart,
                                @RequestParam(required = false) LocalDateTime startTaskTimeEnd) {
        final SearchTaskDto searchTaskDto = SearchTaskDto.builder()
                .name(name)
                .statuses(statuses)
                .executorId(executorId)
                .authorId(authorId)
                .deadlineTimeStart(deadlineTimeStart)
                .deadlineTimeEnd(deadlineTimeEnd)
                .startTaskTimeStart(startTaskTimeStart)
                .startTaskTimeEnd(startTaskTimeEnd)
                .build();

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
