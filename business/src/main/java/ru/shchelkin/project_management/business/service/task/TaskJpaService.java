package ru.shchelkin.project_management.business.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.shchelkin.project_management.business.mapper.TaskMapper;
import ru.shchelkin.project_management.commons.exceptions.ArgumentException;
import ru.shchelkin.project_management.commons.exceptions.BlankException;
import ru.shchelkin.project_management.commons.exceptions.MaxLengthException;
import ru.shchelkin.project_management.commons.exceptions.NullException;
import ru.shchelkin.project_management.commons.exceptions.employee.EmployeeNotFoundException;
import ru.shchelkin.project_management.commons.exceptions.project.ProjectNotFoundException;
import ru.shchelkin.project_management.commons.exceptions.task.TaskIllegalStatusException;
import ru.shchelkin.project_management.commons.exceptions.task.TaskNotFoundException;
import ru.shchelkin.project_management.commons.exceptions.team_member.TeamMemberNotFoundException;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.commons.status.TaskStatus;
import ru.shchelkin.project_management.commons.util.CustomTimeUtils;
import ru.shchelkin.project_management.dao.employee.EmployeeRepository;
import ru.shchelkin.project_management.dao.project.ProjectRepository;
import ru.shchelkin.project_management.dao.task.TaskRepository;
import ru.shchelkin.project_management.dao.task.specification.TaskSpecification;
import ru.shchelkin.project_management.dao.team_member.TeamMemberRepository;
import ru.shchelkin.project_management.dto.request.task.ChangeTaskStatusDto;
import ru.shchelkin.project_management.dto.request.task.CreateTaskDto;
import ru.shchelkin.project_management.dto.request.task.SearchTaskDto;
import ru.shchelkin.project_management.dto.request.task.UpdateTaskDto;
import ru.shchelkin.project_management.dto.response.task.TaskDto;
import ru.shchelkin.project_management.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TaskJpaService implements TaskService {

    private static final int NAME_MAX_LENGTH = 100;

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public TaskJpaService(TaskRepository taskRepository, ProjectRepository projectRepository, TeamMemberRepository teamMemberRepository, EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public TaskDto create(CreateTaskDto createTaskDto) {
        if (!StringUtils.hasText(createTaskDto.getProjectCodeName()))
            throw new BlankException("projectCodeName", "Project code name");

        validateName(createTaskDto.getName());
        validateEstimateHours(createTaskDto.getEstimateHours());
        validateDeadlineDate(createTaskDto.getDeadlineDate());
        validateAuthorId(createTaskDto.getAuthorId());

        final Task task = new Task();
        TaskMapper.map(createTaskDto, task);

        task.setStatus(TaskStatus.NEW);

        final Project project = projectRepository.findByCodename(createTaskDto.getProjectCodeName())
                .orElseThrow(ProjectNotFoundException::new);
        task.setProject(project);

        if (Objects.nonNull(createTaskDto.getExecutorId())) {
            final TeamMember executor = getTeamMember(task.getProject().getTeam(),
                    createTaskDto.getExecutorId());

            task.setExecutor(executor);
        }

        final TeamMember author = getTeamMember(task.getProject().getTeam(),
                createTaskDto.getAuthorId());

        task.setAuthor(author);

        final LocalDateTime now = CustomTimeUtils.nowUtc();
        task.setCreatedAt(now);
        task.setUpdatedAt(now);

        return TaskMapper.getTaskDto(taskRepository.saveAndFlush(task));
    }

    @Override
    @Transactional
    public TaskDto update(UpdateTaskDto updateTaskDto) {
        validateId(updateTaskDto.getId());
        validateName(updateTaskDto.getName());
        validateEstimateHours(updateTaskDto.getEstimateHours());
        validateDeadlineDate(updateTaskDto.getDeadlineDate());

        final Task task = getTask(updateTaskDto.getId());

        task.setName(updateTaskDto.getName());
        task.setDescription(updateTaskDto.getDescription());

        Long currentTaskExecutorId = null;
        if (Objects.nonNull(task.getExecutor()))
            currentTaskExecutorId = task.getExecutor().getId();

        if (!Objects.equals(currentTaskExecutorId, updateTaskDto.getExecutorId())) {
            if (Objects.nonNull(updateTaskDto.getExecutorId())) {
                final TeamMember executor = getTeamMember(task.getProject().getTeam(),
                        updateTaskDto.getExecutorId());

                if (executor.getEmployee().getStatus() == EmployeeStatus.DELETED)
                    throw new ArgumentException("executorId", "Employee should not be deleted.");

                task.setExecutor(executor);
            }
            else task.setExecutor(null);
        }

        task.setEstimateHours(updateTaskDto.getEstimateHours());

        task.setDeadline(updateTaskDto.getDeadlineDate());

        task.setUpdatedAt(CustomTimeUtils.nowUtc());

        return TaskMapper.getTaskDto(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDto> getAll(SearchTaskDto searchTaskDto) {
        return taskRepository.findAll(TaskSpecification.get(searchTaskDto)).stream()
                .map(TaskMapper::getTaskDto)
                .toList();
    }

    @Override
    @Transactional
    public void changeStatus(ChangeTaskStatusDto changeTaskStatusDto) {
        validateId(changeTaskStatusDto.getId());

        final Task task = getTask(changeTaskStatusDto.getId());

        if (isAvailableChangeStatus(task.getStatus(), changeTaskStatusDto.getStatus()))
            task.setStatus(changeTaskStatusDto.getStatus());
        else
            throw new TaskIllegalStatusException(task.getStatus(), changeTaskStatusDto.getStatus());
    }

    public void validateId(Long id) {
        if (Objects.isNull(id))
            throw new NullException("id");
    }

    public void validateName(String name) {
        if (!StringUtils.hasText(name))
            throw new BlankException("name");
        if (name.trim().length() > NAME_MAX_LENGTH)
            throw new MaxLengthException("name", NAME_MAX_LENGTH);
    }

    public void validateEstimateHours(Integer estimateHours) {
        if (Objects.isNull(estimateHours))
            throw new NullException("estimateHours", "Estimate hours");
    }

    public void validateDeadlineDate(LocalDateTime deadline) {
        if (Objects.isNull(deadline))
            throw new NullException("deadlineDate", "Deadline date");

        if (!deadline.isAfter(LocalDateTime.now()))
            throw new ArgumentException("deadlineDate", "Deadline date should not be in the past or now");
    }

    public void validateAuthorId(Long authorId) {
        if (Objects.isNull(authorId))
            throw new NullException("authorId", "Author id");
    }

    private Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
    }

    private TeamMember getTeamMember(ProjectTeam projectTeam, Long employeeId) {
        final Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(EmployeeNotFoundException::new);

        return teamMemberRepository.findByTeamAndEmployee(projectTeam, employee)
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
