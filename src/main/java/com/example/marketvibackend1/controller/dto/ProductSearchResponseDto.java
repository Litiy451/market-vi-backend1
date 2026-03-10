package com.example.marketvibackend1.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class ProductSearchResponseDto {
    private long id;
    private String title;
    private String description;
    private BigDecimal price;
    private int amount;
    private List<String> images;
}
