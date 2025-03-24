package org.example.product.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.example.product.application.port.out.ProductPort;
import org.example.product.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductPort {
    private final MongoProductRepository repository;

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream()
                .map(MongoProductEntity::toDomain)
                .toList();
    }

    @Override
    public Product save(Product product) {
        MongoProductEntity entity = MongoProductEntity.fromDomain(product);
        return repository.save(entity).toDomain();
    }

    @Override
    public Optional<Product> findById(String productId) {
        return repository.findById(productId)
                .map(MongoProductEntity::toDomain);
    }

    @Override
    public void deleteById(String productId) {
        repository.deleteById(productId);
    }
} 