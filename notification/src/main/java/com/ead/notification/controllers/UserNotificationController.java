package com.ead.notification.controllers;

import com.ead.notification.dtos.NotificationDto;
import com.ead.notification.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserNotificationController {
    private final NotificationService notificationService;

    @GetMapping("/users/{userId}/all")
    public ResponseEntity<Page<NotificationDto>> getAllNotificationsByUser(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                                           @PathVariable(value = "userId") UUID userId,
                                                                           Authentication authentication) {
        return ResponseEntity.ok(notificationService.getNotificationsByUser(userId, pageable));
    }

    @PutMapping(value = "/users/{userId}/update")
    public ResponseEntity<NotificationDto> updateNotification(@PathVariable(value = "userId") UUID userId,
                                                              @RequestBody NotificationDto notificationDto) {
        return ResponseEntity.ok(notificationService.updateNotification(userId, notificationDto));
    }
}
