package com.gameStore.ernestasUrbonas.service;

import com.gameStore.ernestasUrbonas.dto.UserDTO;
import com.gameStore.ernestasUrbonas.model.Role;
import com.gameStore.ernestasUrbonas.model.UserEntity;
import com.gameStore.ernestasUrbonas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;


    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userEntity = this.mapDTOToEntity(userDTO);
        UserEntity savedUserEntity = this.userRepository.save(userEntity);
        return this.mapEntityToDTO(savedUserEntity);
    }

    private UserEntity mapDTOToEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(this.encryptPassword(userDTO.getPassword()));
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setRoleEntities(userDTO
                .getRoles()
                .stream()
                .map(roleService::getRoleByName)
                .collect(Collectors.toSet()));
        return userEntity;
    }

    private UserDTO mapEntityToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setRoles(userEntity.getRoleEntities()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        return userDTO;
    }

    private String encryptPassword (String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
