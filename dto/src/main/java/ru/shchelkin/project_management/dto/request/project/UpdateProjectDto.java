package ru.shchelkin.project_management.dto.request.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Project info to update")
public class UpdateProjectDto extends CreateProjectDto {
}
