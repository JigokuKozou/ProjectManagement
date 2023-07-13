package ru.shchelkin.project_management.dao.project_team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shchelkin.project_management.model.Project;
import ru.shchelkin.project_management.model.ProjectTeam;

import java.util.Optional;

@Repository
public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, Long> {
    Optional<ProjectTeam> findByProject(Project project);
}
