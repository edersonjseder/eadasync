package com.ead.notification.repositories;

import com.ead.notification.enums.NotificationStatus;
import com.ead.notification.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID>, JpaSpecificationExecutor<Notification> {
    Page<Notification> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus status, Pageable pageable);
    Optional<Notification> findByIdAndUserId(UUID id, UUID userId);
}
