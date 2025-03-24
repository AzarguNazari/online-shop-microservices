package org.example.orderservice.infrastructure.persistence;

import org.example.orderservice.domain.model.Order;
import org.example.orderservice.domain.port.OrderRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MongoOrderRepository extends MongoRepository<Order, String>, OrderRepository {
    @Override
    default List<Order> findAll() {
        return findAll();
    }

    @Override
    default Optional<Order> findById(String orderId) {
        return findById(orderId);
    }

    @Override
    default Order save(Order order) {
        return save(order);
    }

    @Override
    default void deleteById(String orderId) {
        deleteById(orderId);
    }
} 