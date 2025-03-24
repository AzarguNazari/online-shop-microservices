package org.example.notification.application.service;

import org.example.notification.domain.model.Notification;
import org.example.notification.domain.port.NotificationRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getAllNotifications(String userId, String type, String status, int limit, int offset) {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(String notificationId) {
        return notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    public Notification createNotification(String userId, String message, String type, String channel, Map<String, Object> metadata) {
        Notification notification = Notification.builder()
            .userId(userId)
            .message(message)
            .type(type)
            .channel(channel)
            .sentAt(Instant.now())
            .status("PENDING")
            .metadata(metadata)
            .build();
        return notificationRepository.save(notification);
    }

    public void deleteNotification(String notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    public Notification updateNotificationStatus(String notificationId, String status) {
        Notification notification = getNotificationById(notificationId);
        notification.setStatus(status);
        return notificationRepository.save(notification);
    }
} 