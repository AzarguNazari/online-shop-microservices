package org.example.analytics.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.analytics.infrastructure.persistence.MongoAnalyticsRepository;
import org.example.analytics.infrastructure.persistence.OrderAnalyticsEntity;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderAnalyticsConsumer {

    private final MongoAnalyticsRepository analyticsRepository;

    @RabbitListener(queues = "order-placed-queue")
    public void handleOrderPlaced(Map<String, Object> orderData) {
        log.info("📊 Analytics Service: Aggregating Order Data: {}", orderData);

        OrderAnalyticsEntity analytics = OrderAnalyticsEntity.builder()
                .orderId((String) orderData.get("orderId"))
                .userId((String) orderData.get("userId"))
                .amount((Double) orderData.get("totalAmount"))
                .orderDate(LocalDateTime.now()) // Or parse from orderData
                .status((String) orderData.get("orderStatus"))
                .build();

        analyticsRepository.save(analytics);
        log.info("Analytics data saved for Order ID: {}", analytics.getOrderId());
    }
}
