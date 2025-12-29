package org.example.notification.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class OrderPlacedListener {

    @RabbitListener(queues = "order-placed-queue")
    public void handleOrderPlaced(Map<String, Object> orderData) {
        log.info("🔔 Notification Service: Processing Order Placed Event: {}", orderData);
        String orderId = (String) orderData.get("orderId");
        String userId = (String) orderData.get("userId");
        Double totalAmount = (Double) orderData.get("totalAmount");

        log.info("Sending notification to User {}: Your order {} with amount {} has been placed successfully!",
                userId, orderId, totalAmount);
    }
}
