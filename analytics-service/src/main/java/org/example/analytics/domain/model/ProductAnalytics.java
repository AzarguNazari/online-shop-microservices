package org.example.analytics.domain.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ProductAnalytics {
    private Long id;
    private LocalDate date;
    private String productId;
    private String productName;
    private String category;
    private int unitsSold;
    private double totalRevenue;
} 