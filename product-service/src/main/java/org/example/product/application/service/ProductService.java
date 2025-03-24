package org.example.product.application.service;

import lombok.RequiredArgsConstructor;
import org.example.product.application.port.in.CreateProductCommand;
import org.example.product.application.port.in.ProductUseCase;
import org.example.product.application.port.in.UpdateProductCommand;
import org.example.product.application.port.in.UpdateStockCommand;
import org.example.product.application.port.out.ProductPort;
import org.example.product.domain.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {
    private final ProductPort productPort;

    @Override
    public List<Product> getAllProducts(String category, int limit, int offset) {
        return productPort.findAll();
        // TODO: Implement filtering by category and pagination
    }

    @Override
    public Product createProduct(CreateProductCommand command) {
        Product product = Product.create(
            command.name(),
            command.description(),
            command.price(),
            command.category(),
            command.stockLevel(),
            command.brand(),
            command.sku(),
            command.imageUrl()
        );
        return productPort.save(product);
    }

    @Override
    public Optional<Product> getProductById(String productId) {
        return productPort.findById(productId);
    }

    @Override
    public Optional<Product> updateProduct(String productId, UpdateProductCommand command) {
        return productPort.findById(productId)
            .map(product -> {
                product.update(
                    command.name(),
                    command.description(),
                    command.price(),
                    command.category(),
                    command.brand(),
                    command.sku(),
                    command.imageUrl()
                );
                return productPort.save(product);
            });
    }

    @Override
    public void deleteProduct(String productId) {
        productPort.deleteById(productId);
    }

    @Override
    public Optional<Product> updateProductStock(String productId, UpdateStockCommand command) {
        return productPort.findById(productId)
            .map(product -> {
                product.updateStock(command.stockLevel());
                return productPort.save(product);
            });
    }
} 