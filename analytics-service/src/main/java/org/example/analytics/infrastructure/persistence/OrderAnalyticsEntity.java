package org.example.analytics.infrastructure.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "order_analytics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAnalyticsEntity {
    @Id
    private String id;
    private String orderId;
    private String userId;
    private Double amount;
    private LocalDateTime orderDate;
    private String status;
}
