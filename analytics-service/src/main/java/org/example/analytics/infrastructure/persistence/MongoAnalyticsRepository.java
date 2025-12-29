package org.example.analytics.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoAnalyticsRepository extends MongoRepository<OrderAnalyticsEntity, String> {
}
