package com.gameStore.ernestasUrbonas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

        private Long id;

        @NotNull(message = "Name cannot be null")
        @Size(min = 2, message = "Name must have at least 2 characters")
        private String username;

        @NotNull(message = "Password cannot be null")
        @Size(min = 8, message = "Password must have at least 8 characters")
        private String password;

        @NotNull(message = "Name cannot be null")
        @Size(min = 2, message = "Name must have at least 2 characters")
        private String email;

        private Set<String> roles;
}
