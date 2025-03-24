package org.example.analytics.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.example.analytics.application.port.out.AnalyticsPort;
import org.example.analytics.domain.model.SalesAnalytics;
import org.example.analytics.domain.model.CustomerAnalytics;
import org.example.analytics.domain.model.ProductAnalytics;
import org.example.analytics.infrastructure.adapter.out.persistence.entity.SalesAnalyticsEntity;
import org.example.analytics.infrastructure.adapter.out.persistence.entity.CustomerAnalyticsEntity;
import org.example.analytics.infrastructure.adapter.out.persistence.entity.ProductAnalyticsEntity;
import org.example.analytics.infrastructure.adapter.out.persistence.repository.SalesAnalyticsRepository;
import org.example.analytics.infrastructure.adapter.out.persistence.repository.CustomerAnalyticsRepository;
import org.example.analytics.infrastructure.adapter.out.persistence.repository.ProductAnalyticsRepository;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AnalyticsPersistenceAdapter implements AnalyticsPort {

    private final SalesAnalyticsRepository salesAnalyticsRepository;
    private final CustomerAnalyticsRepository customerAnalyticsRepository;
    private final ProductAnalyticsRepository productAnalyticsRepository;

    @Override
    public List<SalesAnalytics> findSalesAnalytics(LocalDate startDate, LocalDate endDate, String region, String category) {
        return salesAnalyticsRepository.findByDateBetweenAndRegionAndCategory(startDate, endDate, region, category)
            .stream()
            .map(this::mapToSalesAnalytics)
            .collect(Collectors.toList());
    }

    @Override
    public List<CustomerAnalytics> findCustomerAnalytics(LocalDate startDate, LocalDate endDate, String region) {
        return customerAnalyticsRepository.findByDateBetweenAndRegion(startDate, endDate, region)
            .stream()
            .map(this::mapToCustomerAnalytics)
            .collect(Collectors.toList());
    }

    @Override
    public List<ProductAnalytics> findProductAnalytics(LocalDate startDate, LocalDate endDate, String category) {
        return productAnalyticsRepository.findByDateBetweenAndCategory(startDate, endDate, category)
            .stream()
            .map(this::mapToProductAnalytics)
            .collect(Collectors.toList());
    }

    private SalesAnalytics mapToSalesAnalytics(SalesAnalyticsEntity entity) {
        SalesAnalytics domain = new SalesAnalytics();
        domain.setId(entity.getId());
        domain.setDate(entity.getDate());
        domain.setTotalSales(entity.getTotalSales());
        domain.setRegion(entity.getRegion());
        domain.setCategory(entity.getCategory());
        return domain;
    }

    private CustomerAnalytics mapToCustomerAnalytics(CustomerAnalyticsEntity entity) {
        CustomerAnalytics domain = new CustomerAnalytics();
        domain.setId(entity.getId());
        domain.setDate(entity.getDate());
        domain.setNewCustomers(entity.getNewCustomers());
        domain.setReturningCustomers(entity.getReturningCustomers());
        domain.setRegion(entity.getRegion());
        return domain;
    }

    private ProductAnalytics mapToProductAnalytics(ProductAnalyticsEntity entity) {
        ProductAnalytics domain = new ProductAnalytics();
        domain.setId(entity.getId());
        domain.setDate(entity.getDate());
        domain.setProductId(entity.getProductId());
        domain.setProductName(entity.getProductName());
        domain.setCategory(entity.getCategory());
        domain.setUnitsSold(entity.getUnitsSold());
        domain.setTotalRevenue(entity.getTotalRevenue());
        return domain;
    }
} 