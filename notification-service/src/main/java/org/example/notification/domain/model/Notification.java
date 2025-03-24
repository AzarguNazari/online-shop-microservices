package org.example.notification.domain.model;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.util.Map;

@Data
@Builder
public class Notification {
    private String notificationId;
    private String userId;
    private String message;
    private String type;
    private Instant sentAt;
    private String status;
    private String channel;
    private Map<String, Object> metadata;
} 