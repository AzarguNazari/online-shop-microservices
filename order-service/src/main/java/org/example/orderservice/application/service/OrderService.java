package org.example.orderservice.application.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.domain.model.Order;
import org.example.orderservice.domain.port.OrderRepository;
import org.example.orderservice.domain.port.spi.OrderEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    public List<Order> getAllOrders(String status, int limit, int offset) {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(String orderId) {
        return orderRepository.findById(orderId);
    }

    public Order createOrder(Order order) {
        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDateTime.now());
        }
        if (order.getOrderStatus() == null) {
            order.setOrderStatus("PENDING");
        }
        Order savedOrder = orderRepository.save(order);
        orderEventPublisher.publishOrderPlacedEvent(savedOrder);
        return savedOrder;
    }

    public Order updateOrder(String orderId, Order order) {
        order.setOrderId(orderId);
        return orderRepository.save(order);
    }

    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

    public Order updateOrderStatus(String orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }
}
