package org.example.analytics.application.port.in;

import org.example.analytics.domain.model.SalesAnalytics;
import org.example.analytics.domain.model.CustomerAnalytics;
import org.example.analytics.domain.model.ProductAnalytics;
import java.time.LocalDate;
import java.util.List;

public interface AnalyticsUseCase {
    List<SalesAnalytics> getSalesAnalytics(LocalDate startDate, LocalDate endDate, String region, String category);
    List<CustomerAnalytics> getCustomerAnalytics(LocalDate startDate, LocalDate endDate, String region);
    List<ProductAnalytics> getProductAnalytics(LocalDate startDate, LocalDate endDate, String category);
} 