package com.gameStore.ernestasUrbonas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO {

        private Long id;

        @NotNull(message = "Name cannot be null")
        private String name;

        private String location;

        @NotNull(message = "Capacity cannot be null")
        @Size(min = 10, message = "Capacity must be at least 0")
        private Integer capacity;
}
