package org.example.product.application.port.out;

import org.example.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductPort {
    List<Product> findAll();
    Product save(Product product);
    Optional<Product> findById(String productId);
    void deleteById(String productId);
} 