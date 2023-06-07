package ru.shchelkin.project_management.dao.project_team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.shchelkin.project_management.model.ProjectTeam;

public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, Long>, JpaSpecificationExecutor<ProjectTeam> {
}
