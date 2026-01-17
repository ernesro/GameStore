package com.gameStore.ernestasUrbonas.service;

import com.gameStore.ernestasUrbonas.exception.NotFoundException;
import com.gameStore.ernestasUrbonas.model.Role;
import com.gameStore.ernestasUrbonas.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name)
                .orElseThrow(() ->  new NotFoundException("Role not found with identifier: " + name));
    }
}
