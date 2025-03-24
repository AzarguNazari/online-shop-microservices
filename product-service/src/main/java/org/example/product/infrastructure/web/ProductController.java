package org.example.product.infrastructure.web;

import lombok.RequiredArgsConstructor;
import org.example.product.application.port.in.ProductUseCase;
import org.example.product.domain.model.Product;
import org.example.product.application.port.in.CreateProductCommand;
import org.example.product.application.port.in.UpdateProductCommand;
import org.example.product.application.port.in.UpdateStockCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductUseCase productUseCase;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(productUseCase.getAllProducts(category, limit, offset));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request) {
        CreateProductCommand command = new CreateProductCommand(
            request.name(),
            request.description(),
            request.price(),
            request.category(),
            request.stockLevel(),
            request.brand(),
            request.sku(),
            request.imageUrl()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productUseCase.createProduct(command));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId) {
        return productUseCase.getProductById(productId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String productId,
            @RequestBody UpdateProductRequest request) {
        UpdateProductCommand command = UpdateProductCommand.builder()
            .productId(productId)
            .name(request.name())
            .description(request.description())
            .price(request.price())
            .category(request.category())
            .brand(request.brand())
            .sku(request.sku())
            .imageUrl(request.imageUrl())
            .build();
        return productUseCase.updateProduct(productId, command)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        productUseCase.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateProductStock(
            @PathVariable String productId,
            @RequestBody UpdateStockRequest request) {
        UpdateStockCommand command = new UpdateStockCommand(request.stockLevel());
        return productUseCase.updateProductStock(productId, command)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

record CreateProductRequest(
    String name,
    String description,
    BigDecimal price,
    String category,
    int stockLevel,
    String brand,
    String sku,
    String imageUrl
) {}

record UpdateProductRequest(
    String name,
    String description,
    BigDecimal price,
    String category,
    String brand,
    String sku,
    String imageUrl
) {}

record UpdateStockRequest(int stockLevel) {} 