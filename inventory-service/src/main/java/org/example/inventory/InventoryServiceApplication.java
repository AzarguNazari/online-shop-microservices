package org.example.inventory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

}


@Setter
@Getter
@Document(collection = "inventory")
class Inventory {
    @Id
    private String productId;
    private int stockLevel;
    private int reservedStock;
    private LocalDateTime lastUpdated;
    private int lowStockThreshold;
    private boolean isBackordered;
}

@Setter
@Getter
class InventoryCreateRequest {
    private String productId;
    private int stockLevel;
    private int lowStockThreshold;
}

@Setter
@Getter
class InventoryUpdateRequest {
    private int stockLevel;
    private int reservedStock;
    private int lowStockThreshold;
    private boolean isBackordered;
}

@Setter
@Getter
class InventoryStockUpdateRequest {
    private int stockLevel;
}

@Setter
@Getter
class Error {
    private int code;
    private String message;
}

@Repository
interface InventoryRepository extends MongoRepository<Inventory, String> {
    List<Inventory> findByProductId(String productId);
    List<Inventory> findByStockLevelLessThan(int lowStockThreshold);
}

@Service
class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventory(String productId, Boolean lowStock, int limit, int offset) {
        if (productId != null) {
            return inventoryRepository.findByProductId(productId);
        } else if (lowStock != null && lowStock) {
            return inventoryRepository.findByStockLevelLessThan(10);
        } else {
            return inventoryRepository.findAll();
        }
    }

    public Inventory createInventory(InventoryCreateRequest request) {
        Inventory inventory = new Inventory();
        inventory.setProductId(request.getProductId());
        inventory.setStockLevel(request.getStockLevel());
        inventory.setLowStockThreshold(request.getLowStockThreshold());
        inventory.setLastUpdated(LocalDateTime.now());
        return inventoryRepository.save(inventory);
    }

    public Optional<Inventory> getInventoryById(String productId) {
        return inventoryRepository.findById(productId);
    }

    public Optional<Inventory> updateInventory(String productId, InventoryUpdateRequest request) {
        return inventoryRepository.findById(productId).map(inventory -> {
            inventory.setStockLevel(request.getStockLevel());
            inventory.setReservedStock(request.getReservedStock());
            inventory.setLowStockThreshold(request.getLowStockThreshold());
            inventory.setBackordered(request.isBackordered());
            inventory.setLastUpdated(LocalDateTime.now());
            return inventoryRepository.save(inventory);
        });
    }

    public void deleteInventory(String productId) {
        inventoryRepository.deleteById(productId);
    }

    public Optional<Inventory> updateStockLevel(String productId, InventoryStockUpdateRequest request) {
        return inventoryRepository.findById(productId).map(inventory -> {
            inventory.setStockLevel(request.getStockLevel());
            inventory.setLastUpdated(LocalDateTime.now());
            return inventoryRepository.save(inventory);
        });
    }
}

@RestController
@RequestMapping("/inventory")
class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory(
        @RequestParam(required = false) String productId,
        @RequestParam(required = false) Boolean lowStock,
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(inventoryService.getAllInventory(productId, lowStock, limit, offset));
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody InventoryCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createInventory(request));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable String productId) {
        Optional<Inventory> inventory = inventoryService.getInventoryById(productId);
        return inventory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable String productId, @RequestBody InventoryUpdateRequest request) {
        Optional<Inventory> updatedInventory = inventoryService.updateInventory(productId, request);
        return updatedInventory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable String productId) {
        inventoryService.deleteInventory(productId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Inventory> updateStockLevel(@PathVariable String productId, @RequestBody InventoryStockUpdateRequest request) {
        Optional<Inventory> updatedInventory = inventoryService.updateStockLevel(productId, request);
        return updatedInventory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}