package ru.shchelkin.project_management.dto.request.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProjectByIdDto {
    private String codeName;
}
