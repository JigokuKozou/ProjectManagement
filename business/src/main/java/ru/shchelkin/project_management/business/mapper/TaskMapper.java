package ru.shchelkin.project_management.business.mapper;

import ru.shchelkin.project_management.dto.request.task.CreateTaskDto;
import ru.shchelkin.project_management.dto.response.task.TaskCardDto;
import ru.shchelkin.project_management.dto.response.team_member.TeamMemberCardDto;
import ru.shchelkin.project_management.model.Task;

public class TaskMapper {

    public static TaskCardDto getTaskCardDto(Task task) {
        final TaskCardDto taskCardDto = new TaskCardDto();

        map(task, taskCardDto);

        return taskCardDto;
    }

    private static void map(Task from, TaskCardDto to) {
        to.setId(from.getId());
        to.setName(from.getName());
        to.setDescription(from.getDescription());

        final TeamMemberCardDto executor = TeamMemberMapper.getTeamMemberCardDto(from.getExecutor());
        final TeamMemberCardDto author = TeamMemberMapper.getTeamMemberCardDto(from.getAuthor());
        to.setExecutor(executor);
        to.setAuthor(author);

        to.setEstimateHours(from.getEstimateHours());
        to.setDeadLineDate(from.getDeadline());
        to.setStatus(from.getStatus());
        to.setCreatedAt(from.getCreatedAt());
        to.setUpdatedAt(from.getUpdatedAt());
    }

    public static void map(CreateTaskDto from, Task to) {
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setEstimateHours(from.getEstimateHours());
        to.setDeadline(from.getDeadLineDate());
    }
}
