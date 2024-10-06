package com.tienda.tiendaJuegos.dto;

import com.tienda.tiendaJuegos.model.enums.Category;
import com.tienda.tiendaJuegos.model.enums.Condition;
import com.tienda.tiendaJuegos.model.enums.Tag;
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
    private Condition condition;

    @NotNull(message = "Category cannot be null")
    private Category category;

    @NotEmpty(message = "Tags cannot be empty")
    private List<Tag> tags;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @DecimalMax(value = "10000.0", message = "Price must be less than 10,000")
    private double price;

    @DecimalMin(value = "0", message = "Stock cannot be negative")
    private int stock;

    @NotNull(message = "Image URL cannot be null")
    @Size(min = 3, message = "Image URL must have at least 3 characters")
    private String imageUrl;

    @DecimalMin(value = "0.0", message = "Average rating must be at least 0")
    @DecimalMax(value = "5.0", message = "Average rating must be at most 5")
    private double averageRating;

}

