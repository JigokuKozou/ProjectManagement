package ru.shchelkin.project_management.dao.team_member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shchelkin.project_management.model.Employee;
import ru.shchelkin.project_management.model.TeamMember;

import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    Optional<TeamMember> findByEmployee(Employee employee);

    Optional<TeamMember> findByEmployeeId(Long employeeId);
}
