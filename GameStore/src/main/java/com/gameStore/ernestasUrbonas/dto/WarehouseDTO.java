package com.gameStore.ernestasUrbonas.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
        @Min(100)
        @Max(10000)
        private Long capacity;
}
