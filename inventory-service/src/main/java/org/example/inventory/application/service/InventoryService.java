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

    public java.util.List<Inventory> getAllInventory(String productId, Boolean lowStock) {
        if (productId != null) {
            return inventoryRepository.findByProductId(productId)
                    .map(java.util.List::of)
                    .orElse(java.util.List.of());
        }
        java.util.List<Inventory> all = inventoryRepository.findAll();
        if (Boolean.TRUE.equals(lowStock)) {
            return all.stream().filter(Inventory::isLowStock).collect(java.util.stream.Collectors.toList());
        }
        return all;
    }

    public Optional<Inventory> getInventoryById(String productId) {
        return inventoryRepository.findByProductId(productId);
    }

    public Optional<Inventory> updateInventory(String productId, int stockLevel, int reservedStock,
            int lowStockThreshold, boolean isBackordered) {
        return inventoryRepository.findByProductId(productId).map(inventory -> {
            inventory.setStockLevel(stockLevel);
            inventory.setReservedStock(reservedStock);
            inventory.setLowStockThreshold(lowStockThreshold);
            inventory.setBackordered(isBackordered);
            inventoryRepository.save(inventory);
            return inventory;
        });
    }

    public void deleteInventory(String productId) {
        inventoryRepository.deleteByProductId(productId);
    }

    public Optional<Inventory> updateStockLevel(String productId, int newStockLevel) {
        return inventoryRepository.findByProductId(productId).map(inventory -> {
            inventory.updateStockLevel(newStockLevel);
            inventoryRepository.save(inventory);
            return inventory;
        });
    }
}