package org.example.analytics.domain.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class SalesAnalytics {
    private Long id;
    private LocalDate date;
    private double totalSales;
    private String region;
    private String category;
} 