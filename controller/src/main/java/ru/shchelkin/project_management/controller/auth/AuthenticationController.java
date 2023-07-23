package ru.shchelkin.project_management.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shchelkin.project_management.business.service.auth.AuthenticationService;
import ru.shchelkin.project_management.dto.request.auth.AuthenticationRequestDto;
import ru.shchelkin.project_management.dto.response.auth.AuthenticationResponseDto;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "AuthenticationController", description = "Controller for authentication")
public class AuthenticationController {

    private final AuthenticationService authService;

    @Autowired
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @Operation(description = "Get JWT(Bearer) token")
    @PostMapping("/authenticate")
    public AuthenticationResponseDto authenticate(@RequestBody AuthenticationRequestDto authRequest) {
        return authService.authenticate(authRequest);
    }
}
