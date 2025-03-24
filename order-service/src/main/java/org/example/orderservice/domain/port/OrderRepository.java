package org.example.orderservice.domain.port;

import org.example.orderservice.domain.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findAll();
    Optional<Order> findById(String orderId);
    Order save(Order order);
    void deleteById(String orderId);
} 