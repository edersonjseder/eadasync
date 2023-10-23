package com.ead.notification.services;

import com.ead.notification.dtos.NotificationCommandDto;
import com.ead.notification.enums.NotificationStatus;
import com.ead.notification.models.Notification;
import com.ead.notification.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void saveNotification(NotificationCommandDto notificationCommandDto) {
        var notification = new Notification();
        BeanUtils.copyProperties(notificationCommandDto, notification);
        notification.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        notification.setNotificationStatus(NotificationStatus.CREATED);
        notificationRepository.save(notification);
    }
}
