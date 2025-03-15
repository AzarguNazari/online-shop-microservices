package org.example.product;

import lombok.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
class Product {
    @Id
    private String productId = UUID.randomUUID().toString();
    private String name;
    private String description;
    private double price;
    private String category;
    private int stockLevel;
    private String brand;
    private String sku;
    private String imageUrl;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ProductCreateRequest {
    private String name;
    private String description;
    private double price;
    private String category;
    private int stockLevel;
    private String brand;
    private String sku;
    private String imageUrl;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ProductUpdateRequest {
    private String name;
    private String description;
    private double price;
    private String category;
    private String brand;
    private String sku;
    private String imageUrl;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ProductStockUpdateRequest {
    private int stockLevel;
}

@Data
@AllArgsConstructor
class Error {
    private int code;
    private String message;
}

@Repository
interface ProductRepository extends MongoRepository<Product, String> {}

@Service
@RequiredArgsConstructor
class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts(String category, int limit, int offset) {
        return productRepository.findAll();
    }

    public Product createProduct(ProductCreateRequest request) {
        return productRepository.save(new Product(
            UUID.randomUUID().toString(), request.getName(), request.getDescription(), request.getPrice(),
            request.getCategory(), request.getStockLevel(), request.getBrand(), request.getSku(), request.getImageUrl()
        ));
    }

    public Optional<Product> getProductById(String productId) {
        return productRepository.findById(productId);
    }

    public Optional<Product> updateProduct(String productId, ProductUpdateRequest request) {
        return productRepository.findById(productId).map(product -> {
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setCategory(request.getCategory());
            product.setBrand(request.getBrand());
            product.setSku(request.getSku());
            product.setImageUrl(request.getImageUrl());
            return productRepository.save(product);
        });
    }

    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }

    public Optional<Product> updateProductStock(String productId, ProductStockUpdateRequest request) {
        return productRepository.findById(productId).map(product -> {
            product.setStockLevel(request.getStockLevel());
            return productRepository.save(product);
        });
    }
}

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
        @RequestParam(required = false) String category,
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(productService.getAllProducts(category, limit, offset));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId) {
        return productService.getProductById(productId)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable String productId, @RequestBody ProductUpdateRequest request) {
        return productService.updateProduct(productId, request)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateProductStock(@PathVariable String productId, @RequestBody ProductStockUpdateRequest request) {
        return productService.updateProductStock(productId, request)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
