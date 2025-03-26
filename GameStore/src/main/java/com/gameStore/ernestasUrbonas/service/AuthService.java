package com.gameStore.ernestasUrbonas.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailService = customUserDetailService;
        this.jwtService = jwtService;
    }

    public String authenticateAndGenerateToken(String username, String password) throws AuthenticationException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        final UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

        return jwtService.generateToken(userDetails);
    }
}
