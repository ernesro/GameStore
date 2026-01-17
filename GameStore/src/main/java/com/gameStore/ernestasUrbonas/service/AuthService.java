package com.gameStore.ernestasUrbonas.service;

import com.gameStore.ernestasUrbonas.dto.AuthRequest;
import com.gameStore.ernestasUrbonas.dto.AuthResponse;
import com.gameStore.ernestasUrbonas.model.Role;
import com.gameStore.ernestasUrbonas.model.UserEntity;
import com.gameStore.ernestasUrbonas.repository.UserRepository;
import com.gameStore.ernestasUrbonas.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authManager,
                       UserRepository userRepository,
                       JwtUtil jwtUtil,
                       PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roles = user.getRoleEntities().stream().map(Role::getName).collect(Collectors.toList());
        String token = jwtUtil.generateToken(user.getUsername(), roles);

        return new AuthResponse(token, user.getUsername(), roles);
    }

    public AuthResponse register(String username, String rawPassword) {
        UserEntity u = new UserEntity();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(u);
        return login(new AuthRequest() {{ setUsername(username); setPassword(rawPassword);} });
    }
}
