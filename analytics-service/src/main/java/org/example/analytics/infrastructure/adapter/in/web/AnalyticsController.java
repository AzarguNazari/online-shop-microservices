package org.example.analytics.infrastructure.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.example.analytics.application.port.in.AnalyticsUseCase;
import org.example.analytics.domain.model.SalesAnalytics;
import org.example.analytics.domain.model.CustomerAnalytics;
import org.example.analytics.domain.model.ProductAnalytics;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsUseCase analyticsUseCase;

    @GetMapping("/summary")
    public ResponseEntity<java.util.Map<String, Object>> getSummary() {
        return ResponseEntity.ok(java.util.Map.of(
                "revenue", "$45,231.89",
                "activeUsers", 12482,
                "ordersToday", 156));
    }

    @GetMapping("/sales")
    public ResponseEntity<List<SalesAnalytics>> getSalesAnalytics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(analyticsUseCase.getSalesAnalytics(startDate, endDate, region, category));
    }

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerAnalytics>> getCustomerAnalytics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String region) {
        return ResponseEntity.ok(analyticsUseCase.getCustomerAnalytics(startDate, endDate, region));
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductAnalytics>> getProductAnalytics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(analyticsUseCase.getProductAnalytics(startDate, endDate, category));
    }
}