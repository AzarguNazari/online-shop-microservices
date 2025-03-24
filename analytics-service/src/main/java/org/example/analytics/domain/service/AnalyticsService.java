package org.example.analytics.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.analytics.application.port.in.AnalyticsUseCase;
import org.example.analytics.application.port.out.AnalyticsPort;
import org.example.analytics.domain.model.SalesAnalytics;
import org.example.analytics.domain.model.CustomerAnalytics;
import org.example.analytics.domain.model.ProductAnalytics;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService implements AnalyticsUseCase {
    
    private final AnalyticsPort analyticsPort;

    @Override
    public List<SalesAnalytics> getSalesAnalytics(LocalDate startDate, LocalDate endDate, String region, String category) {
        return analyticsPort.findSalesAnalytics(startDate, endDate, region, category);
    }

    @Override
    public List<CustomerAnalytics> getCustomerAnalytics(LocalDate startDate, LocalDate endDate, String region) {
        return analyticsPort.findCustomerAnalytics(startDate, endDate, region);
    }

    @Override
    public List<ProductAnalytics> getProductAnalytics(LocalDate startDate, LocalDate endDate, String category) {
        return analyticsPort.findProductAnalytics(startDate, endDate, category);
    }
} 