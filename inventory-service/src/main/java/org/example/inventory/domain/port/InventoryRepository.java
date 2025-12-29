package org.example.inventory.domain.port;

import org.example.inventory.domain.model.Inventory;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    Optional<Inventory> findByProductId(String productId);

    List<Inventory> findAll();

    void save(Inventory inventory);

    void deleteByProductId(String productId);
}