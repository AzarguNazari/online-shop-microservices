package org.example.inventory.application.service;

import lombok.RequiredArgsConstructor;
import org.example.inventory.domain.model.Inventory;
import org.example.inventory.domain.port.InventoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventory(String productId, Boolean lowStock) {
        if (productId != null) {
            return inventoryRepository.findByProductId(productId);
        } else if (lowStock != null && lowStock) {
            return inventoryRepository.findByLowStock();
        } else {
            return inventoryRepository.findAll();
        }
    }

    public Inventory createInventory(String productId, int stockLevel, int lowStockThreshold) {
        Inventory inventory = new Inventory(productId, stockLevel, lowStockThreshold);
        return inventoryRepository.save(inventory);
    }

    public Optional<Inventory> getInventoryById(String productId) {
        return inventoryRepository.findById(productId);
    }

    public Optional<Inventory> updateInventory(String productId, int stockLevel, int reservedStock, 
                                            int lowStockThreshold, boolean isBackordered) {
        return inventoryRepository.findById(productId).map(inventory -> {
            inventory.updateStockLevel(stockLevel);
            inventory.updateReservedStock(reservedStock);
            inventory.updateLowStockThreshold(lowStockThreshold);
            inventory.setBackordered(isBackordered);
            return inventoryRepository.save(inventory);
        });
    }

    public void deleteInventory(String productId) {
        inventoryRepository.deleteById(productId);
    }

    public Optional<Inventory> updateStockLevel(String productId, int newStockLevel) {
        return inventoryRepository.findById(productId).map(inventory -> {
            inventory.updateStockLevel(newStockLevel);
            return inventoryRepository.save(inventory);
        });
    }
} 