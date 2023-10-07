package com.ead.authuser.exceptions;

import java.util.UUID;

public class UserCourseNotFoundException extends RuntimeException {
    public UserCourseNotFoundException(UUID value) {
        super(generateMessage(value));
    }
    private static String generateMessage(UUID value) {
        return "No user related to the course with value: " + value;
    }
}
