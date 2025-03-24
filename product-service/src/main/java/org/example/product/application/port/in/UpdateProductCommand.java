package org.example.product.application.port.in;

import lombok.Builder;
import java.math.BigDecimal;

@Builder
public record UpdateProductCommand(
    String productId,
    String name,
    String description,
    BigDecimal price,
    String category,
    String brand,
    String sku,
    String imageUrl
) {}
