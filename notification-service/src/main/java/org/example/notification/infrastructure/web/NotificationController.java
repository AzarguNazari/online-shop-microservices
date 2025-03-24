package org.example.notification.infrastructure.web;

import lombok.Data;
import org.example.notification.application.service.NotificationService;
import org.example.notification.domain.model.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        List<Notification> notifications = notificationService.getAllNotifications(userId, type, status, limit, offset);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable String notificationId) {
        Notification notification = notificationService.getNotificationById(notificationId);
        return ResponseEntity.ok(notification);
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody NotificationCreateRequest request) {
        Notification notification = notificationService.createNotification(
            request.getUserId(),
            request.getMessage(),
            request.getType(),
            request.getChannel(),
            request.getMetadata()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable String notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{notificationId}/status")
    public ResponseEntity<Notification> updateNotificationStatus(
            @PathVariable String notificationId,
            @RequestBody NotificationStatusUpdateRequest request) {
        Notification notification = notificationService.updateNotificationStatus(notificationId, request.getStatus());
        return ResponseEntity.ok(notification);
    }

    @Data
    static class NotificationCreateRequest {
        private String userId;
        private String message;
        private String type;
        private String channel;
        private Map<String, Object> metadata;
    }

    @Data
    static class NotificationStatusUpdateRequest {
        private String status;
    }
} 