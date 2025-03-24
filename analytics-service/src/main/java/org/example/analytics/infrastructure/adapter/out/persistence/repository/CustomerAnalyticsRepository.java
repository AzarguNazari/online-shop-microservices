package org.example.analytics.infrastructure.adapter.out.persistence.repository;

import org.example.analytics.infrastructure.adapter.out.persistence.entity.CustomerAnalyticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomerAnalyticsRepository extends JpaRepository<CustomerAnalyticsEntity, Long> {
    List<CustomerAnalyticsEntity> findByDateBetweenAndRegion(
        LocalDate startDate, 
        LocalDate endDate, 
        String region
    );
} 