package org.example.orderservice.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.domain.model.Order;
import org.example.orderservice.domain.model.OrderItem;
import org.example.orderservice.domain.port.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {
    private final JpaOrderRepository jpaOrderRepository;

    @Override
    public List<Order> findAll() {
        return jpaOrderRepository.findAll().stream()
                .map(this::mapToOrder)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return jpaOrderRepository.findById(orderId)
                .map(this::mapToOrder);
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = mapToEntity(order);
        return mapToOrder(jpaOrderRepository.save(entity));
    }

    @Override
    public void deleteById(String orderId) {
        jpaOrderRepository.deleteById(orderId);
    }

    private Order mapToOrder(OrderEntity entity) {
        return new Order(
                entity.getOrderId(),
                entity.getUserId(),
                entity.getOrderDate(),
                entity.getOrderStatus(),
                entity.getTotalAmount(),
                entity.getShippingAddress(),
                entity.getBillingAddress(),
                entity.getPaymentMethod(),
                entity.getPaymentStatus(),
                entity.getOrderItems().stream().map(this::mapToOrderItem).collect(Collectors.toList()),
                entity.getTrackingNumber(),
                entity.getNotes());
    }

    private OrderItem mapToOrderItem(OrderItemEntity entity) {
        return new OrderItem(
                entity.getOrderItemId(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getTotalPrice(),
                entity.getDiscountApplied());
    }

    private OrderEntity mapToEntity(Order order) {
        return OrderEntity.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .totalAmount(order.getTotalAmount())
                .shippingAddress(order.getShippingAddress())
                .billingAddress(order.getBillingAddress())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .orderItems(order.getOrderItems().stream().map(this::mapToItemEntity).collect(Collectors.toList()))
                .trackingNumber(order.getTrackingNumber())
                .notes(order.getNotes())
                .build();
    }

    private OrderItemEntity mapToItemEntity(OrderItem item) {
        return OrderItemEntity.builder()
                .orderItemId(item.getOrderItemId())
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalPrice())
                .discountApplied(item.getDiscountApplied())
                .build();
    }
}
