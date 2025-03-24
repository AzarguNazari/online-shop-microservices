package org.example.orderservice.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String orderId;
    private String userId;
    private LocalDateTime orderDate;
    private String orderStatus;
    private double totalAmount;
    private String shippingAddress;
    private String billingAddress;
    private String paymentMethod;
    private String paymentStatus;
    private List<OrderItem> orderItems;
    private String trackingNumber;
    private String notes;
} 