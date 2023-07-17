package ru.shchelkin.project_management.business.mapper;

import ru.shchelkin.project_management.commons.util.CustomStringUtils;
import ru.shchelkin.project_management.dto.request.task.CreateTaskDto;
import ru.shchelkin.project_management.dto.response.task.TaskDto;
import ru.shchelkin.project_management.dto.response.team_member.TeamMemberDto;
import ru.shchelkin.project_management.model.Task;

import java.util.Objects;

public class TaskMapper {

    public static TaskDto getTaskDto(Task task) {
        final TaskDto taskDto = new TaskDto();

        map(task, taskDto);

        return taskDto;
    }

    private static void map(Task from, TaskDto to) {
        to.setId(from.getId());
        to.setName(from.getName());
        to.setDescription(from.getDescription());

        if (Objects.nonNull(from.getExecutor())) {
            final TeamMemberDto executor = TeamMemberMapper.getTeamMemberDto(from.getExecutor());
            to.setExecutor(executor);
        }

        final TeamMemberDto author = TeamMemberMapper.getTeamMemberDto(from.getAuthor());
        to.setAuthor(author);

        to.setEstimateHours(from.getEstimateHours());
        to.setDeadlineDate(from.getDeadline());
        to.setStatus(from.getStatus());
        to.setCreatedAt(from.getCreatedAt());
        to.setUpdatedAt(from.getUpdatedAt());
    }

    public static void map(CreateTaskDto from, Task to) {
        to.setName(from.getName().strip());
        to.setDescription(CustomStringUtils.strip(from.getDescription()));
        to.setEstimateHours(from.getEstimateHours());
        to.setDeadline(from.getDeadlineDate());
    }
}
