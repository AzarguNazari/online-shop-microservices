package org.example.orderservice.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private String orderItemId;
    private String productId;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private double discountApplied;
} 