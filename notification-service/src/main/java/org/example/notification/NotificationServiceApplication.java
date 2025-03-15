package org.example.notification;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Data
@Document(collection = "notifications")
class Notification {
    @Id
    private String notificationId;
    private String userId;
    private String message;
    private String type;
    private Instant sentAt;
    private String status;
    private String channel;
    private Map<String, Object> metadata;
}


@Data
class NotificationCreateRequest {
    private String userId;
    private String message;
    private String type;
    private String channel;
    private Map<String, Object> metadata;
}

@Data
class NotificationStatusUpdateRequest {
    private String status;
}


@Data
class ErrorResponse {
    private int code;
    private String message;
}

@Repository
interface NotificationRepository extends MongoRepository<Notification, String> {
}

interface NotificationService {
    List<Notification> getAllNotifications(String userId, String type, String status, int limit, int offset);
    Notification getNotificationById(String notificationId);
    Notification createNotification(NotificationCreateRequest request);
    void deleteNotification(String notificationId);
    Notification updateNotificationStatus(String notificationId, NotificationStatusUpdateRequest request);
}

@Service
class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> getAllNotifications(String userId, String type, String status, int limit, int offset) {
        return notificationRepository.findAll();
    }

    @Override
    public Notification getNotificationById(String notificationId) {
        return notificationRepository.findById(notificationId).orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    @Override
    public Notification createNotification(NotificationCreateRequest request) {
        Notification notification = new Notification();
        notification.setUserId(request.getUserId());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());
        notification.setChannel(request.getChannel());
        notification.setSentAt(Instant.now());
        notification.setStatus("PENDING");
        notification.setMetadata(request.getMetadata());
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(String notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public Notification updateNotificationStatus(String notificationId, NotificationStatusUpdateRequest request) {
        Notification notification = getNotificationById(notificationId);
        notification.setStatus(request.getStatus());
        return notificationRepository.save(notification);
    }
}

@RestController
@RequestMapping("/notifications")
class NotificationController {

    @Autowired
    private NotificationService notificationService;

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
        Notification notification = notificationService.createNotification(request);
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
        Notification notification = notificationService.updateNotificationStatus(notificationId, request);
        return ResponseEntity.ok(notification);
    }
}

@SpringBootApplication
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}