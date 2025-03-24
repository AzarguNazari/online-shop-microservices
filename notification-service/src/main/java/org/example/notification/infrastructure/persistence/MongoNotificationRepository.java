package org.example.notification.infrastructure.persistence;

import org.example.notification.domain.model.Notification;
import org.example.notification.domain.port.NotificationRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class MongoNotificationRepository implements NotificationRepository {
    private final MongoTemplate mongoTemplate;
    private static final String COLLECTION_NAME = "notifications";

    public MongoNotificationRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Notification> findAll() {
        return mongoTemplate.findAll(Notification.class, COLLECTION_NAME);
    }

    @Override
    public Optional<Notification> findById(String notificationId) {
        Query query = new Query(Criteria.where("notificationId").is(notificationId));
        return Optional.ofNullable(mongoTemplate.findOne(query, Notification.class, COLLECTION_NAME));
    }

    @Override
    public Notification save(Notification notification) {
        return mongoTemplate.save(notification, COLLECTION_NAME);
    }

    @Override
    public void deleteById(String notificationId) {
        Query query = new Query(Criteria.where("notificationId").is(notificationId));
        mongoTemplate.remove(query, COLLECTION_NAME);
    }
} 