package org.example.inventory.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.example.inventory.domain.model.Inventory;
import org.example.inventory.domain.port.InventoryRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoInventoryRepository implements InventoryRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Inventory> findAll() {
        return mongoTemplate.findAll(Inventory.class);
    }

    @Override
    public List<Inventory> findByProductId(String productId) {
        Query query = new Query(Criteria.where("productId").is(productId));
        return mongoTemplate.find(query, Inventory.class);
    }

    @Override
    public List<Inventory> findByLowStock() {
        Query query = new Query(Criteria.where("stockLevel").lt("lowStockThreshold"));
        return mongoTemplate.find(query, Inventory.class);
    }

    @Override
    public Optional<Inventory> findById(String productId) {
        Query query = new Query(Criteria.where("productId").is(productId));
        return Optional.ofNullable(mongoTemplate.findOne(query, Inventory.class));
    }

    @Override
    public Inventory save(Inventory inventory) {
        return mongoTemplate.save(inventory);
    }

    @Override
    public void deleteById(String productId) {
        Query query = new Query(Criteria.where("productId").is(productId));
        mongoTemplate.remove(query, Inventory.class);
    }
} 