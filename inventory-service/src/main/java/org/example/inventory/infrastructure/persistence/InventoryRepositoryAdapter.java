package org.example.inventory.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.example.inventory.domain.model.Inventory;
import org.example.inventory.domain.port.InventoryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InventoryRepositoryAdapter implements InventoryRepository {
    private final JpaInventoryRepository jpaInventoryRepository;

    @Override
    public Optional<Inventory> findByProductId(String productId) {
        return jpaInventoryRepository.findById(productId)
                .map(this::mapToDomain);
    }

    @Override
    public void save(Inventory inventory) {
        jpaInventoryRepository.save(mapToEntity(inventory));
    }

    private Inventory mapToDomain(InventoryEntity entity) {
        Inventory inventory = new Inventory(
                entity.getProductId(),
                entity.getStockLevel(),
                entity.getLowStockThreshold());
        inventory.setReservedStock(entity.getReservedStock());
        inventory.setLastUpdated(entity.getLastUpdated());
        inventory.setBackordered(entity.isBackordered());
        return inventory;
    }

    private InventoryEntity mapToEntity(Inventory inventory) {
        return InventoryEntity.builder()
                .productId(inventory.getProductId())
                .stockLevel(inventory.getStockLevel())
                .reservedStock(inventory.getReservedStock())
                .lastUpdated(inventory.getLastUpdated())
                .lowStockThreshold(inventory.getLowStockThreshold())
                .isBackordered(inventory.isBackordered())
                .build();
    }
}
