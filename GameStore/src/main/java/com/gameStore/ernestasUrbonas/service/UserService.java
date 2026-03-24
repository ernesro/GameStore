package com.gameStore.ernestasUrbonas.service;

import com.gameStore.ernestasUrbonas.dto.UserDTO;
import com.gameStore.ernestasUrbonas.mapper.UserMapper;
import com.gameStore.ernestasUrbonas.model.UserEntity;
import com.gameStore.ernestasUrbonas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create a new user.
     *
     * @param userDTO Data Transfer Object containing user details.
     * @return The created UserDTO.
     */
    public UserDTO createUser(UserDTO userDTO) {

        userDTO.setPassword(this.encryptPassword(userDTO.getPassword()));

        UserEntity userEntity = this.userMapper.mapDTOToEntity(userDTO);

        userEntity.setRoleEntities(userDTO
                .getRoles()
                .stream()
                .map(roleService::getRoleByName)
                .collect(Collectors.toSet()));

        UserEntity savedUserEntity = this.userRepository.save(userEntity);
        return this.userMapper.mapEntityToDTO(savedUserEntity);
    }

    /**
     * Encrypt the user's password using BCrypt.
     *
     * @param password
     * @return
     */
    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
