package org.example.product.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;
import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class Product {
    private String productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private int stockLevel;
    private String brand;
    private String sku;
    private String imageUrl;

    public static Product create(
            String name,
            String description,
            BigDecimal price,
            String category,
            int stockLevel,
            String brand,
            String sku,
            String imageUrl
    ) {
        return Product.builder()
                .productId(UUID.randomUUID().toString())
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

    public void updateStock(int newStockLevel) {
        this.stockLevel = newStockLevel;
    }

    public void update(
            String name,
            String description,
            BigDecimal price,
            String category,
            String brand,
            String sku,
            String imageUrl
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.brand = brand;
        this.sku = sku;
        this.imageUrl = imageUrl;
    }
} 