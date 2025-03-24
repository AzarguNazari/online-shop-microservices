package org.example.inventory.domain.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Inventory {
    private String productId;
    private int stockLevel;
    private int reservedStock;
    private LocalDateTime lastUpdated;
    private int lowStockThreshold;
    private boolean isBackordered;

    public Inventory(String productId, int stockLevel, int lowStockThreshold) {
        this.productId = productId;
        this.stockLevel = stockLevel;
        this.lowStockThreshold = lowStockThreshold;
        this.lastUpdated = LocalDateTime.now();
        this.reservedStock = 0;
        this.isBackordered = false;
    }

    public void updateStockLevel(int newStockLevel) {
        this.stockLevel = newStockLevel;
        this.lastUpdated = LocalDateTime.now();
    }

    public void updateReservedStock(int newReservedStock) {
        this.reservedStock = newReservedStock;
        this.lastUpdated = LocalDateTime.now();
    }

    public void updateLowStockThreshold(int newThreshold) {
        this.lowStockThreshold = newThreshold;
        this.lastUpdated = LocalDateTime.now();
    }

    public void setBackordered(boolean backordered) {
        isBackordered = backordered;
        this.lastUpdated = LocalDateTime.now();
    }

    public boolean isLowStock() {
        return stockLevel < lowStockThreshold;
    }
} 