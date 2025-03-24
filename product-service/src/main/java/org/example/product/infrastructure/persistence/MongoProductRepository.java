package org.example.product.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MongoProductRepository extends MongoRepository<MongoProductEntity, String> {} 