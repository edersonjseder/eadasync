package com.ead.notification.services;

import com.ead.notification.dtos.NotificationCommandDto;
import com.ead.notification.dtos.NotificationDto;
import com.ead.notification.enums.NotificationStatus;
import com.ead.notification.exceptions.NotificationNotFoundException;
import com.ead.notification.models.Notification;
import com.ead.notification.repositories.NotificationRepository;
import com.ead.notification.utils.NotificationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static com.ead.notification.constants.NotificationConstants.NOTIFICATION_NOT_FOUND_MENSAGEM;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationUtils notificationUtils;

    public void saveNotification(NotificationCommandDto notificationCommandDto) {
        var notification = new Notification();
        BeanUtils.copyProperties(notificationCommandDto, notification);
        notification.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        notification.setNotificationStatus(NotificationStatus.CREATED);
        notificationRepository.save(notification);
    }

    public Page<NotificationDto> getNotificationsByUser(UUID userId, Pageable pageable) {
        return notificationUtils
                .toListNotificationDto(notificationRepository
                        .findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED, pageable));
    }

    @Transactional
    public NotificationDto updateNotification(UUID userId, NotificationDto notificationDto) {
        var notification = notificationRepository.findByIdAndUserId(notificationDto.getId(), userId)
                .orElseThrow(() -> new NotificationNotFoundException(userId));

        notification.setNotificationStatus(notificationDto.getStatus());

        return notificationUtils.toNotificationDto(notification);
    }
}
