package com.gameStore.ernestasUrbonas.controller;

import com.gameStore.ernestasUrbonas.dto.UserDTO;
import com.gameStore.ernestasUrbonas.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create a new user.
     *
     * @param userDTO Data Transfer Object containing user details for creation.
     * @return The created userDTO.
     */
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with the provided details.",
            operationId = "createUser",
            tags = { "users" },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User created successfully",
                            content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserDTO.class)
                            )
                    )
            }
    )

    @PostMapping
    public ResponseEntity<UserDTO> createProduct(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
