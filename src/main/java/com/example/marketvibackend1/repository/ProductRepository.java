package com.example.marketvibackend1.repository;

import com.example.marketvibackend1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
