package com.example.marketvibackend1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", length = 2000)
    private String description;
    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
    @Column(name = "amount", nullable = false)
    private int amount;
    @ElementCollection
    @CollectionTable(
            name = "product_images",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "image_url", nullable = false, length = 1000)
    private List<String> images = new ArrayList<>();

    public Product(long id, String title, String description, BigDecimal price, int amount, List<String> images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.images = images;
    }

    public Product(){}
}
