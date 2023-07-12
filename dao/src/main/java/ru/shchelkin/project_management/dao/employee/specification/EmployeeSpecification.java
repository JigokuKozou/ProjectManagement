package ru.shchelkin.project_management.dao.employee.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.dto.request.employee.SearchEmployeeDto;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;
import ru.shchelkin.project_management.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class EmployeeSpecification {
    public static Specification<Employee> get(SearchEmployeeDto searchDto) {
        return (root, query, criteriaBuilder) -> {
            final List<Predicate> searchPredicates = new ArrayList<>(6);
            
            addLikeCaseInsensitivePredicate(searchDto.getSurname(), Employee_.SURNAME,
                    root, criteriaBuilder, searchPredicates);

            addLikeCaseInsensitivePredicate(searchDto.getName(), Employee_.NAME,
                    root, criteriaBuilder, searchPredicates);

            addLikeCaseInsensitivePredicate(searchDto.getPatronymic(), Employee_.PATRONYMIC,
                    root, criteriaBuilder, searchPredicates);

            addLikeCaseInsensitivePredicate(searchDto.getLogin(), Employee_.LOGIN,
                    root, criteriaBuilder, searchPredicates);

            addLikeCaseInsensitivePredicate(searchDto.getEmail(), Employee_.EMAIL,
                    root, criteriaBuilder, searchPredicates);

            searchPredicates.add(
                    criteriaBuilder.equal(root.get(Employee_.STATUS), EmployeeStatus.ACTIVE.ordinal())
            );

            return query.where(criteriaBuilder.and(searchPredicates.toArray(new Predicate[0])))
                    .getRestriction();
        };
    }

    public static Specification<Employee> get(FilterEmployeeByTeamRoleDto filterDao) {
        return ((root, query, criteriaBuilder) -> {
            Subquery<Long> employeeIdQuery = query.subquery(Long.class);
            Root<TeamMember> teamMemberRoot = employeeIdQuery.from(TeamMember.class);

            Join<TeamMember, ProjectTeam> projectTeamJoin = teamMemberRoot.join(TeamMember_.TEAM, JoinType.LEFT);
            Join<ProjectTeam, Project> projectJoin = projectTeamJoin.join(ProjectTeam_.PROJECT, JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>(2);

            if (!ObjectUtils.isEmpty(filterDao.getProjectCodeName()))
                predicates.add(criteriaBuilder.equal(
                        projectJoin.get(Project_.CODE_NAME), filterDao.getProjectCodeName()
                ));

            if (Objects.nonNull(filterDao.getTeamRole()))
                predicates.add(criteriaBuilder.equal(
                        teamMemberRoot.get(TeamMember_.ROLE), filterDao.getTeamRole().ordinal()
                ));

            predicates.add(
                    criteriaBuilder.equal(root.get(Employee_.STATUS), EmployeeStatus.ACTIVE.ordinal())
            );

            employeeIdQuery.select(teamMemberRoot.get(TeamMember_.EMPLOYEE).get(Employee_.ID))
                    .where(criteriaBuilder.and(predicates.toArray(Predicate[]::new)));

            return root.get(Employee_.ID).in(employeeIdQuery);
        });
    }

    private static void addLikeCaseInsensitivePredicate(String value, String fieldName,
                                                        Path<?> root, CriteriaBuilder criteriaBuilder,
                                                        List<Predicate> searchPredicates) {
        if (!ObjectUtils.isEmpty(value)) {
            searchPredicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(fieldName)), '%' + value.toLowerCase() + '%'
            ));
        }
    }
}
