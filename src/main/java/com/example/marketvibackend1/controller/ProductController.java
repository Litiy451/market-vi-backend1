package com.example.marketvibackend1.controller;

import com.example.marketvibackend1.controller.dto.CreateProductRequest;
import com.example.marketvibackend1.controller.dto.ProductResponse;
import com.example.marketvibackend1.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PostMapping
    public ProductResponse create(@Valid @RequestBody CreateProductRequest req) {
        return productService.create(req);
    }
}
