package ru.shchelkin.project_management.dto.response.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Response with info for authentication")
public class AuthenticationResponseDto {

    @Schema(description = "Employee JWT(Bearer) token")
    private String token;
}
