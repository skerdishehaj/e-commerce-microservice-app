package org.skerdians.ecommerce.notification.repository;

import org.skerdians.ecommerce.notification.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
