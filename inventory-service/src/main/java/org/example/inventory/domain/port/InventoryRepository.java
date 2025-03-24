package org.example.inventory.domain.port;

import org.example.inventory.domain.model.Inventory;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    List<Inventory> findAll();
    List<Inventory> findByProductId(String productId);
    List<Inventory> findByLowStock();
    Optional<Inventory> findById(String productId);
    Inventory save(Inventory inventory);
    void deleteById(String productId);
} 