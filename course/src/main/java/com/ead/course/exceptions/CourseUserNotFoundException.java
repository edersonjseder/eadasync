package com.ead.course.exceptions;

import java.util.UUID;

public class CourseUserNotFoundException extends RuntimeException {
    public CourseUserNotFoundException(String value) {
        super(value);
    }
    public CourseUserNotFoundException(UUID value) {
        super(generateMessage(value));
    }
    private static String generateMessage(UUID value) {
        return "No course related to the user with value: " + value;
    }
}
