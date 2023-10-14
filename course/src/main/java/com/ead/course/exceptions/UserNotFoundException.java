package com.ead.course.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String value) {
        super(value);
    }
    public UserNotFoundException(UUID value) {
        super(generateMessage(value));
    }
    private static String generateMessage(UUID value) {
        return "No course related to the user with value: " + value;
    }
}
