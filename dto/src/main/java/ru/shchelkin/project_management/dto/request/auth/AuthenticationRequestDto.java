package ru.shchelkin.project_management.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Employee information for authentication")
public class AuthenticationRequestDto {

    @Schema(description = "Employee login")
    private String login;

    @Schema(description = "Employee password")
    private String password;
}
