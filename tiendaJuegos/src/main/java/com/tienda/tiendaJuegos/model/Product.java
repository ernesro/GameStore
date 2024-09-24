package com.tienda.tiendaJuegos.model;

import com.tienda.tiendaJuegos.model.enums.Category;
import com.tienda.tiendaJuegos.model.enums.Condition;
import com.tienda.tiendaJuegos.model.enums.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ElementCollection
    private List<Tag> tags;

    private double price;
    private int stock;
    private String imageUrl;
    private String averageRating;

}
