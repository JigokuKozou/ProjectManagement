package ru.shchelkin.project_management.controller.task;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/tasks")
@Tag(name = "TaskController", description = "Controller for tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Create task")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto create(@RequestBody @Valid CreateTaskDto createTaskDto) {
        return taskService.create(createTaskDto);
    }

    @Operation(summary = "Update task")
    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskDto update(@RequestBody @Valid UpdateTaskDto updateTaskDto,
                          @PathVariable Long id) {
        updateTaskDto.setId(id);
        return taskService.update(updateTaskDto);
    }

    @Operation(summary = "Get task")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskDto get(@Parameter(description = "Unique identifier") @PathVariable Long id) {
        return taskService.get(id);
    }

    @Operation(summary = "Search tasks")
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDto> search(@Parameter(description = "Name (case-insensitive)")
                                    @RequestParam(required = false) String name,
                                @Parameter(description = "Statuses")
                                    @RequestParam(required = false) List<TaskStatus> statuses,
                                @Parameter(description = "Employee executor unique identifier")
                                    @RequestParam(required = false) Long executorId,
                                @Parameter(description = "Employee author unique identifier")
                                    @RequestParam(required = false) Long authorId,
                                @Parameter(description = "Deadline start period")
                                    @RequestParam(required = false) LocalDateTime deadlineStart,
                                @Parameter(description = "Deadline end period")
                                    @RequestParam(required = false) LocalDateTime deadlineEnd,
                                @Parameter(description = "Created at start period")
                                    @RequestParam(required = false) LocalDateTime createdAtStart,
                                @Parameter(description = "Created at end period")
                                    @RequestParam(required = false) LocalDateTime createdAtEnd) {
        final SearchTaskDto searchTaskDto = SearchTaskDto.builder()
                .name(name)
                .statuses(statuses)
                .executorId(executorId)
                .authorId(authorId)
                .deadlineStart(deadlineStart)
                .deadlineEnd(deadlineEnd)
                .createdAtStart(createdAtStart)
                .createdAtEnd(createdAtEnd)
                .build();

        return taskService.getAll(searchTaskDto);
    }

    @Operation(summary = "Change task status")
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeStatus(@RequestBody @Valid ChangeTaskStatusDto changeTaskStatusDto,
                             @PathVariable Long id) {
        changeTaskStatusDto.setId(id);
        taskService.changeStatus(changeTaskStatusDto);
    }
}
