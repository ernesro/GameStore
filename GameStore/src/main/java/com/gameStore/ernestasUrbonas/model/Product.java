package com.gameStore.ernestasUrbonas.model;

import com.gameStore.ernestasUrbonas.model.enums.Category;
import com.gameStore.ernestasUrbonas.model.enums.Condition;
import com.gameStore.ernestasUrbonas.model.enums.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(unique = true, nullable = false)
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Tag> tags;

    private double price;

    @Column(nullable = false)
    private int stock;

    private String imageUrl;

    @Column(nullable = false)
    private double averageRating;

}
