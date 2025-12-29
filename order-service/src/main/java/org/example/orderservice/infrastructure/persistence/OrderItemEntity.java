package org.example.orderservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderItemId;
    private String productId;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private double discountApplied;
}
