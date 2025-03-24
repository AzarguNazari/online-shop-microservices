package org.example.product.infrastructure.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import org.example.product.domain.model.Product;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

@Document(collection = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MongoProductEntity {
    @Id
    private String productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private int stockLevel;
    private String brand;
    private String sku;
    private String imageUrl;

    public static MongoProductEntity fromDomain(Product product) {
        return MongoProductEntity.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .stockLevel(product.getStockLevel())
                .brand(product.getBrand())
                .sku(product.getSku())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public Product toDomain() {
        return Product.builder()
                .productId(productId)
                .name(name)
                .description(description)
                .price(price)
                .category(category)
                .stockLevel(stockLevel)
                .brand(brand)
                .sku(sku)
                .imageUrl(imageUrl)
                .build();
    }
} 