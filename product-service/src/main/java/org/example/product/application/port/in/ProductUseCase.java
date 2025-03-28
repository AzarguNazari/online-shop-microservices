package org.example.product.application.port.in;

import org.example.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductUseCase {
    List<Product> getAllProducts(String category, int limit, int offset);
    Product createProduct(CreateProductCommand command);
    Optional<Product> getProductById(String productId);
    Optional<Product> updateProduct(String productId, UpdateProductCommand command);
    void deleteProduct(String productId);
    Optional<Product> updateProductStock(String productId, UpdateStockCommand command);
}
