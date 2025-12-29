package org.example.orderservice.domain.port.spi;

import org.example.orderservice.domain.model.Order;

public interface OrderEventPublisher {
    void publishOrderPlacedEvent(Order order);
}
