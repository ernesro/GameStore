package com.gameStore.ernestasUrbonas.mapper;

import com.gameStore.ernestasUrbonas.dto.UserDTO;
import com.gameStore.ernestasUrbonas.model.Role;
import com.gameStore.ernestasUrbonas.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserEntity mapDTOToEntity(UserDTO userDTO) {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setEmail(userDTO.getEmail());
        return userEntity;
    }

    public UserDTO mapEntityToDTO(UserEntity userEntity) {

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
}
