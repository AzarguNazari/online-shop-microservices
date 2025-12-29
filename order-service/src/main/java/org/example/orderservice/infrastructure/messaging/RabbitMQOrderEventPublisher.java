package org.example.orderservice.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.domain.model.Order;
import org.example.orderservice.domain.port.spi.OrderEventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQOrderEventPublisher implements OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    public static final String EXCHANGE_NAME = "order-exchange";
    public static final String ROUTING_KEY = "order.placed";

    @Override
    public void publishOrderPlacedEvent(Order order) {
        log.info("Publishing Order Placed Event for Order ID: {}", order.getOrderId());
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, order);
    }
}
