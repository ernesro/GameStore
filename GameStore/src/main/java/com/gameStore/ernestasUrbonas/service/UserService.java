package com.gameStore.ernestasUrbonas.service;

import com.gameStore.ernestasUrbonas.model.Role;
import com.gameStore.ernestasUrbonas.model.User;
import com.gameStore.ernestasUrbonas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String authorities = user.getRoleEntities().stream()
                .map(Role::getName)
                .collect(Collectors.joining(", "));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(authorities)
                .build();
    }
}
