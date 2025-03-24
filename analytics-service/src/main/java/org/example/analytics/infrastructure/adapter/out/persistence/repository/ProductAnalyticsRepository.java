package org.example.analytics.infrastructure.adapter.out.persistence.repository;

import org.example.analytics.infrastructure.adapter.out.persistence.entity.ProductAnalyticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductAnalyticsRepository extends JpaRepository<ProductAnalyticsEntity, Long> {
    List<ProductAnalyticsEntity> findByDateBetweenAndCategory(
        LocalDate startDate, 
        LocalDate endDate, 
        String category
    );
} 