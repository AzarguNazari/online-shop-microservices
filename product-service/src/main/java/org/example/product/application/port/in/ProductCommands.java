package org.example.product.application.port.in;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ProductCommands {
    String name;
    String description;
    double price;
    String category;
    int stockLevel;
    String brand;
    String sku;
    String imageUrl;
}
