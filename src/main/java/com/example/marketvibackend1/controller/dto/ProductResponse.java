package com.example.marketvibackend1.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private long id;
    private String title;
    private String description;
    private BigDecimal price;
    private int amount;
    private List<String> images;
}
