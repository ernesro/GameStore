package com.gameStore.ernestasUrbonas.controller;

import com.gameStore.ernestasUrbonas.dto.AuthRequest;
import com.gameStore.ernestasUrbonas.dto.AuthResponse;
import com.gameStore.ernestasUrbonas.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authenticate user and generate JWT token.
     *
     * @param request AuthRequest containing username and password.
     * @return AuthResponse containing JWT token.
     */
    @Operation(
            summary = "Authenticate user and generate JWT token",
            description = "Authenticates the user with provided credentials and returns a JWT token upon successful authentication.",
            operationId = "createToken",
            tags = { "authentication" },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Authentication successful, JWT token generated",
                            content =  @Content(
                                    mediaType = "application/json",
                                    schema =  @Schema(implementation = AuthResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Authentication failed, invalid credentials"
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse res = authService.login(request);
        return ResponseEntity.ok(res);
    }
}
