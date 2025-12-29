package org.example.inventory.application.service;

import lombok.RequiredArgsConstructor;
import org.example.inventory.domain.model.Inventory;
import org.example.inventory.domain.port.InventoryRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public Inventory createInventory(String productId, int stockLevel, int lowStockThreshold) {
        Inventory inventory = new Inventory(productId, stockLevel, lowStockThreshold);
        inventoryRepository.save(inventory);
        return inventory;
    }

    public Optional<Inventory> getInventoryByProductId(String productId) {
        return inventoryRepository.findByProductId(productId);
    }

    public Optional<Inventory> updateStockLevel(String productId, int newStockLevel) {
        return inventoryRepository.findByProductId(productId).map(inventory -> {
            inventory.updateStockLevel(newStockLevel);
            inventoryRepository.save(inventory);
            return inventory;
        });
    }
}