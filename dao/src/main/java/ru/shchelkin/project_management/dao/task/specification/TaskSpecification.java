package ru.shchelkin.project_management.dao.task.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.shchelkin.project_management.dto.request.task.SearchTaskDto;
import ru.shchelkin.project_management.model.Employee_;
import ru.shchelkin.project_management.model.Task;
import ru.shchelkin.project_management.model.Task_;
import ru.shchelkin.project_management.model.TeamMember_;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskSpecification {
    public static Specification<Task> get(SearchTaskDto searchDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> searchPredicates = new ArrayList<>();

            if (Objects.nonNull(searchDto.getName()))
                searchPredicates.add(criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(Task_.NAME)),
                                '%'+ searchDto.getName().toLowerCase() +'%'
                ));

            if (Objects.nonNull(searchDto.getStatuses()) && !searchDto.getStatuses().isEmpty())
                searchPredicates.add(root.get(Task_.STATUS).in(searchDto.getStatuses()));

            if (Objects.nonNull(searchDto.getExecutorId()))
                searchPredicates.add(
                        root.get(Task_.EXECUTOR).get(TeamMember_.EMPLOYEE).get(Employee_.ID)
                                .in(searchDto.getExecutorId())
                );

            if (Objects.nonNull(searchDto.getAuthorId()))
                searchPredicates.add(
                        root.get(Task_.AUTHOR).get(TeamMember_.EMPLOYEE).get(Employee_.ID)
                                .in(searchDto.getAuthorId())
                );

            // Deadline period
            if (Objects.nonNull(searchDto.getDeadlineStart()))
                searchPredicates.add(
                        criteriaBuilder.greaterThan(root.get(Task_.DEADLINE), searchDto.getDeadlineStart())
                );

            if (Objects.nonNull(searchDto.getDeadlineEnd()))
                searchPredicates.add(
                        criteriaBuilder.lessThan(root.get(Task_.DEADLINE), searchDto.getDeadlineEnd())
                );

            // Create period
            if (Objects.nonNull(searchDto.getCreatedAtStart()))
                searchPredicates.add(
                        criteriaBuilder.greaterThan(root.get(Task_.CREATED_AT), searchDto.getCreatedAtStart())
                );

            if (Objects.nonNull(searchDto.getCreatedAtEnd()))
                searchPredicates.add(
                        criteriaBuilder.lessThan(root.get(Task_.CREATED_AT), searchDto.getCreatedAtEnd())
                );

            return query.where(criteriaBuilder.and(searchPredicates.toArray(Predicate[]::new)))
                    .orderBy(criteriaBuilder.desc(root.get(Task_.CREATED_AT)))
                    .getRestriction();
        };
    }
}
