package org.example.inventory.infrastructure.web;

import lombok.RequiredArgsConstructor;
import org.example.inventory.application.service.InventoryService;
import org.example.inventory.domain.model.Inventory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory(
            @RequestParam(required = false) String productId,
            @RequestParam(required = false) Boolean lowStock) {
        return ResponseEntity.ok(inventoryService.getAllInventory(productId, lowStock));
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody InventoryCreateRequest request) {
        Inventory inventory = inventoryService.createInventory(
            request.getProductId(),
            request.getStockLevel(),
            request.getLowStockThreshold()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(inventory);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable String productId) {
        Optional<Inventory> inventory = inventoryService.getInventoryById(productId);
        return inventory.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Inventory> updateInventory(
            @PathVariable String productId,
            @RequestBody InventoryUpdateRequest request) {
        Optional<Inventory> updatedInventory = inventoryService.updateInventory(
            productId,
            request.getStockLevel(),
            request.getReservedStock(),
            request.getLowStockThreshold(),
            request.isBackordered()
        );
        return updatedInventory.map(ResponseEntity::ok)
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable String productId) {
        inventoryService.deleteInventory(productId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Inventory> updateStockLevel(
            @PathVariable String productId,
            @RequestBody InventoryStockUpdateRequest request) {
        Optional<Inventory> updatedInventory = inventoryService.updateStockLevel(
            productId,
            request.getStockLevel()
        );
        return updatedInventory.map(ResponseEntity::ok)
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

@lombok.Data
class InventoryCreateRequest {
    private String productId;
    private int stockLevel;
    private int lowStockThreshold;
}

@lombok.Data
class InventoryUpdateRequest {
    private int stockLevel;
    private int reservedStock;
    private int lowStockThreshold;
    private boolean isBackordered;
}

@lombok.Data
class InventoryStockUpdateRequest {
    private int stockLevel;
} 