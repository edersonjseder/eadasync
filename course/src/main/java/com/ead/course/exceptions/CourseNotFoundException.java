package com.ead.course.exceptions;

import java.util.UUID;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(UUID value) {
        super(generateMessage(value));
    }

    private static String generateMessage(UUID value) {
        return "Course not found with value: " + value;
    }
}
