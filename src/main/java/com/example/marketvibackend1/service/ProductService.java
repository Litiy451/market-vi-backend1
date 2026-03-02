package com.example.marketvibackend1.service;

import com.example.marketvibackend1.controller.dto.CreateProductRequest;
import com.example.marketvibackend1.controller.dto.ProductResponse;
import com.example.marketvibackend1.model.Product;
import com.example.marketvibackend1.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse getById(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return toResponse(p);
    }

    public ProductResponse create(CreateProductRequest req) {
        Product p = new Product();
        p.setTitle(req.getTitle());
        p.setDescription(req.getDescription());
        p.setPrice(req.getPrice());
        p.setAmount(req.getAmount());

        if (req.getImages() != null) {
            p.setImages(req.getImages());
        }

        return toResponse(productRepository.save(p));
    }

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                p.getPrice(),
                p.getAmount(),
                p.getImages()
        );
    }
}
