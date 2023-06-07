package ru.shchelkin.project_management.dao.employee.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.dto.request.employee.SearchEmployeeDto;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;
import ru.shchelkin.project_management.model.Employee;
import ru.shchelkin.project_management.model.Project;
import ru.shchelkin.project_management.model.ProjectTeam;
import ru.shchelkin.project_management.model.TeamMember;

import java.util.ArrayList;
import java.util.List;

public final class EmployeeSpecification {
    public static Specification<Employee> get(SearchEmployeeDto searchDto) {
        return (root, query, criteriaBuilder) -> {
            final List<Predicate> searchPredicates = new ArrayList<>();
            if (!ObjectUtils.isEmpty(searchDto.getSurname()))
                searchPredicates.add(criteriaBuilder.like(root.get("surname"), '%' + searchDto.getSurname() + '%'));

            if (!ObjectUtils.isEmpty(searchDto.getName()))
                searchPredicates.add(criteriaBuilder.like(root.get("name"), '%' + searchDto.getName() + '%'));

            if (!ObjectUtils.isEmpty(searchDto.getPatronymic()))
                searchPredicates.add(criteriaBuilder.like(root.get("patronymic"), '%' + searchDto.getPatronymic() + '%'));

            if (!ObjectUtils.isEmpty(searchDto.getLogin()))
                searchPredicates.add(criteriaBuilder.like(root.get("login"), '%' + searchDto.getLogin() + '%'));

            if (!ObjectUtils.isEmpty(searchDto.getEmail()))
                searchPredicates.add(criteriaBuilder.like(root.get("email"), '%' + searchDto.getEmail() + '%'));

            if (!ObjectUtils.isEmpty(searchDto.getEmail()))
                searchPredicates.add(criteriaBuilder.equal(root.get("status"), EmployeeStatus.ACTIVE.ordinal()));

            if (searchPredicates.isEmpty())
                query.where().getRestriction();

            return query.where(criteriaBuilder.and(searchPredicates.toArray(new Predicate[0])))
                    .getRestriction();
        };
    }

    public static Specification<Employee> get(FilterEmployeeByTeamRoleDto filterDao) {
        return ((root, query, criteriaBuilder) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<TeamMember> subRoot = subquery.from(TeamMember.class);

            Join<TeamMember, ProjectTeam> projectTeamJoin = subRoot.join("team", JoinType.LEFT);
            Join<ProjectTeam, Project> projectJoin = projectTeamJoin.join("project", JoinType.LEFT);

            subquery.select(subRoot.get("employee").get("id"));
            subquery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.like(projectJoin.get("codeName"), filterDao.getProjectCodeName()),
                            criteriaBuilder.equal(subRoot.get("role"), filterDao.getTeamRole().ordinal())
                    )
            );

            return root.get("id").in(subquery);
        });
    }
}
