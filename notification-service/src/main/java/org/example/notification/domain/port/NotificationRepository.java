package org.example.notification.domain.port;

import org.example.notification.domain.model.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
    List<Notification> findAll();
    Optional<Notification> findById(String notificationId);
    Notification save(Notification notification);
    void deleteById(String notificationId);
} 