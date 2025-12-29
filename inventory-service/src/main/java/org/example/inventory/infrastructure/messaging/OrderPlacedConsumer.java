package org.example.inventory.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.inventory.domain.model.Inventory;
import org.example.inventory.domain.port.spi.InventoryRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPlacedConsumer {

    private final InventoryRepository inventoryRepository;

    @RabbitListener(queues = "order-placed-queue")
    public void handleOrderPlaced(Map<String, Object> orderData) {
        log.info("Received Order Placed Event: {}", orderData);
        // Realistic demo: extract order items and update stock
        // For simplicity, we assume orderData has a list of items with productId and
        // quantity
        // In a real app, you'd use a shared DTO

        @SuppressWarnings("unchecked")
        var items = (java.util.List<Map<String, Object>>) orderData.get("orderItems");
        if (items != null) {
            for (var item : items) {
                String productId = (String) item.get("productId");
                Integer quantity = (Integer) item.get("quantity");

                inventoryRepository.findByProductId(productId).ifPresent(inventory -> {
                    inventory.updateStockLevel(inventory.getStockLevel() - quantity);
                    inventoryRepository.save(inventory);
                    log.info("Updated stock for product {}: new level {}", productId, inventory.getStockLevel());
                });
            }
        }
    }
}
