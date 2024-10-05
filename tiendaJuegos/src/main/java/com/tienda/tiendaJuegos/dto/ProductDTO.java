package com.tienda.tiendaJuegos.dto;

import com.tienda.tiendaJuegos.model.enums.Category;
import com.tienda.tiendaJuegos.model.enums.Condition;
import com.tienda.tiendaJuegos.model.enums.Tag;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 3, message = "Name must not be empty")
    private String name;

    private String description;
    private Condition condition;
    private Category category;
    private List<Tag> tags;
    private double price;
    private int stock;
    private String imageUrl;
    private String averageRating;

}

