package ru.shchelkin.project_management.dto.request.employee;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Employee info to update")
public class UpdateEmployeeDto extends CreateEmployeeDto {

    @Hidden
    private Long id;
}
