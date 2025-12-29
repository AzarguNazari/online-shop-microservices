package org.example.inventory.domain.port;

import org.example.inventory.domain.model.Inventory;
import java.util.Optional;

public interface InventoryRepository {
    Optional<Inventory> findByProductId(String productId);

    void save(Inventory inventory);
}