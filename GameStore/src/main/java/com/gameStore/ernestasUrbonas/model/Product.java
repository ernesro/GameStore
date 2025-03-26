package com.gameStore.ernestasUrbonas.model;

import com.gameStore.ernestasUrbonas.model.enums.ItemCategory;
import com.gameStore.ernestasUrbonas.model.enums.ItemCondition;
import com.gameStore.ernestasUrbonas.model.enums.ItemTag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    @Column(name = "itemcondition")
    @Enumerated(EnumType.STRING)
    private ItemCondition itemCondition;

    @Column(name = "itemcategory")
    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;

    @Column(name = "itemtags")
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<ItemTag> itemTags = new ArrayList<>();

    private double price;

    @Column(nullable = false)
    private int stock;

    private String imageUrl;

    @Column(nullable = false)
    private double averageRating;

}
