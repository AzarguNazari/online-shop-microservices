package org.example.analytics.infrastructure.adapter.out.persistence.repository;

import org.example.analytics.infrastructure.adapter.out.persistence.entity.SalesAnalyticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalesAnalyticsRepository extends JpaRepository<SalesAnalyticsEntity, Long> {
    List<SalesAnalyticsEntity> findByDateBetweenAndRegionAndCategory(
        LocalDate startDate, 
        LocalDate endDate, 
        String region, 
        String category
    );
} 