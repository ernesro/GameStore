package com.gameStore.ernestasUrbonas.controller;

import com.gameStore.ernestasUrbonas.dto.AuthRequest;
import com.gameStore.ernestasUrbonas.dto.AuthResponse;
import com.gameStore.ernestasUrbonas.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
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

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> createToken(@RequestBody AuthRequest request) {
        try {
            String jwtToken = authService.authenticateAndGenerateToken(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new AuthResponse(jwtToken));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
