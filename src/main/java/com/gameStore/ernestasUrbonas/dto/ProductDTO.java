package com.gameStore.ernestasUrbonas.dto;

import com.gameStore.ernestasUrbonas.model.enums.ItemCategory;
import com.gameStore.ernestasUrbonas.model.enums.ItemCondition;
import com.gameStore.ernestasUrbonas.model.enums.ItemTag;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name must have at least 2 characters")
    private String name;

    @NotNull(message = "Description cannot be null")
    @Size(min = 2, message = "Description must have at least 2 characters")
    private String description;

    @NotNull(message = "Condition cannot be null")
    private ItemCondition itemCondition;

    @NotNull(message = "Category cannot be null")
    private ItemCategory itemCategory;

    @NotEmpty(message = "Tags cannot be empty")
    private List<ItemTag> itemTags;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @DecimalMax(value = "10000.0", message = "Price must be less than 10,000")
    private double price;

    @NotNull(message = "Image URL cannot be null")
    @Size(min = 3, message = "Image URL must have at least 3 characters")
    private String imageUrl;

    @DecimalMin(value = "0.0", message = "Average rating must be at least 0")
    @DecimalMax(value = "5.0", message = "Average rating must be at most 5")
    private double averageRating;

}

