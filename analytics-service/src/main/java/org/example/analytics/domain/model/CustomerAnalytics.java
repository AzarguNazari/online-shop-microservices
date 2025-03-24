package org.example.analytics.domain.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class CustomerAnalytics {
    private Long id;
    private LocalDate date;
    private int newCustomers;
    private int returningCustomers;
    private String region;
} 