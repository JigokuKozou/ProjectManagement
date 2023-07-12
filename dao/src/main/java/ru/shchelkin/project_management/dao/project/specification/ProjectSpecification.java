package ru.shchelkin.project_management.dao.project.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.shchelkin.project_management.dto.request.project.SearchProjectDto;
import ru.shchelkin.project_management.model.Project;
import ru.shchelkin.project_management.model.Project_;

import java.util.Objects;

public class ProjectSpecification {
    public static Specification<Project> get(SearchProjectDto searchDto) {
        return (root, query, criteriaBuilder) -> {
            Predicate searchPredicate = root.get(Project_.STATUS).in(searchDto.getStatuses());

            if (Objects.nonNull(searchDto.getFilter())) {
                searchPredicate = criteriaBuilder.and(
                        searchPredicate,
                        criteriaBuilder.or(
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get(Project_.CODE_NAME)),
                                        "%" + searchDto.getFilter().toLowerCase() + "%"
                                ),
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get(Project_.NAME)),
                                        "%" + searchDto.getFilter().toLowerCase() + "%"
                                )
                        )
                );
            }

            return query.where(searchPredicate).getRestriction();
        };
    }
}
