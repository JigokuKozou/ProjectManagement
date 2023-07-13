package ru.shchelkin.project_management.business.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shchelkin.project_management.business.mapper.TaskMapper;
import ru.shchelkin.project_management.commons.exceptions.task.TaskIllegalStatusException;
import ru.shchelkin.project_management.commons.exceptions.task.TaskNotFoundException;
import ru.shchelkin.project_management.commons.exceptions.team_member.TeamMemberNotFoundException;
import ru.shchelkin.project_management.commons.status.TaskStatus;
import ru.shchelkin.project_management.dao.task.TaskRepository;
import ru.shchelkin.project_management.dao.task.specification.TaskSpecification;
import ru.shchelkin.project_management.dao.team_member.TeamMemberRepository;
import ru.shchelkin.project_management.dto.request.task.ChangeTaskStatusDto;
import ru.shchelkin.project_management.dto.request.task.CreateTaskDto;
import ru.shchelkin.project_management.dto.request.task.SearchTaskDto;
import ru.shchelkin.project_management.dto.request.task.UpdateTaskDto;
import ru.shchelkin.project_management.dto.response.task.TaskCardDto;
import ru.shchelkin.project_management.model.Task;
import ru.shchelkin.project_management.model.TeamMember;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TaskJpaService implements TaskService {

    private final TaskRepository taskRepository;

    private final TeamMemberRepository teamMemberRepository;

    @Autowired
    public TaskJpaService(TaskRepository taskRepository, TeamMemberRepository teamMemberRepository) {
        this.taskRepository = taskRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    @Override
    @Transactional
    public TaskCardDto create(CreateTaskDto createTaskDto) {
        final Task task = new Task();
        TaskMapper.map(createTaskDto, task);

        task.setStatus(TaskStatus.NEW);

        final TeamMember executor = getTeamMember(createTaskDto.getExecutorId());
        final TeamMember author = getTeamMember(createTaskDto.getAuthorId());
        task.setExecutor(executor);
        task.setAuthor(author);

        final LocalDateTime now = LocalDateTime.now();
        task.setCreatedAt(now);
        task.setUpdatedAt(now);

        return TaskMapper.getTaskCardDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public TaskCardDto update(UpdateTaskDto updateTaskDto) {
        final Task task = getTask(updateTaskDto.getId());

        task.setName(updateTaskDto.getName());
        task.setDescription(updateTaskDto.getDescription());

        if (!Objects.equals(task.getExecutor().getId(), updateTaskDto.getId()))
            task.setExecutor(getTeamMember(updateTaskDto.getExecutorId()));

        task.setEstimateHours(updateTaskDto.getEstimateHours());
        task.setDeadline(updateTaskDto.getDeadLineDate());

        return TaskMapper.getTaskCardDto(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskCardDto> getAll(SearchTaskDto searchTaskDto) {
        return taskRepository.findAll(TaskSpecification.get(searchTaskDto)).stream()
                .map(TaskMapper::getTaskCardDto)
                .toList();
    }

    @Override
    @Transactional
    public void changeStatus(ChangeTaskStatusDto changeTaskStatusDto) {
        final Task task = getTask(changeTaskStatusDto.getId());

        if (isAvailableChangeStatus(task.getStatus(), changeTaskStatusDto.getStatus()))
            task.setStatus(changeTaskStatusDto.getStatus());
        else
            throw new TaskIllegalStatusException(task.getStatus(), changeTaskStatusDto.getStatus());
    }

    private Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
    }

    private TeamMember getTeamMember(Long employeeId) {
        return teamMemberRepository.findByEmployeeId(employeeId)
                .orElseThrow(TeamMemberNotFoundException::new);
    }

    private static boolean isAvailableChangeStatus(TaskStatus current, TaskStatus value) {
        return switch (current) {
            case NEW -> value == TaskStatus.IN_PROGRESS;
            case IN_PROGRESS -> value == TaskStatus.DONE;
            case DONE -> value == TaskStatus.CLOSED;
            case CLOSED -> false;
        };
    }
}
