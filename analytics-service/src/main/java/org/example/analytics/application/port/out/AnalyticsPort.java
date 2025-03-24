package org.example.analytics.application.port.out;

import org.example.analytics.domain.model.SalesAnalytics;
import org.example.analytics.domain.model.CustomerAnalytics;
import org.example.analytics.domain.model.ProductAnalytics;
import java.time.LocalDate;
import java.util.List;

public interface AnalyticsPort {
    List<SalesAnalytics> findSalesAnalytics(LocalDate startDate, LocalDate endDate, String region, String category);
    List<CustomerAnalytics> findCustomerAnalytics(LocalDate startDate, LocalDate endDate, String region);
    List<ProductAnalytics> findProductAnalytics(LocalDate startDate, LocalDate endDate, String category);
} 