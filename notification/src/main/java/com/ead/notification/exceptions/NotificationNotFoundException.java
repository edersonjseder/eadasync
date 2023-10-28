package com.ead.notification.exceptions;

import java.util.UUID;

import static com.ead.notification.constants.NotificationConstants.NOTIFICATION_NOT_FOUND_MENSAGEM;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(UUID value) {
        super(generateMessage(value));
    }

    private static String generateMessage(UUID value) {
        return NOTIFICATION_NOT_FOUND_MENSAGEM + value;
    }
}
