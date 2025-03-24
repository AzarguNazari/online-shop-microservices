package org.example.product.application.port.in;

import java.math.BigDecimal;

public record CreateProductCommand(
    String name,
    String description,
    BigDecimal price,
    String category,
    int stockLevel,
    String brand,
    String sku,
    String imageUrl
) {} 