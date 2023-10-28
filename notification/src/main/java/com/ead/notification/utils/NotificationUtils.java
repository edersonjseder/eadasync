package com.ead.notification.utils;

import com.ead.notification.dtos.NotificationDto;
import com.ead.notification.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class NotificationUtils {
    public NotificationDto toNotificationDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .title(notification.getTitle())
                .status(notification.getNotificationStatus())
                .message(notification.getMessage())
                .build();
    }
    public Page<NotificationDto> toListNotificationDto(Page<Notification> notifications) {
        return notifications.map(notification -> NotificationDto.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .title(notification.getTitle())
                .status(notification.getNotificationStatus())
                .message(notification.getMessage())
                .build());
    }
}
