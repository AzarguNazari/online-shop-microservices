package org.example.inventory.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEntity {
    @Id
    private String productId;
    private int stockLevel;
    private int reservedStock;
    private LocalDateTime lastUpdated;
    private int lowStockThreshold;
    private boolean isBackordered;
}
