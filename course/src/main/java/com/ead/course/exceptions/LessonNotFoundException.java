package com.ead.course.exceptions;

import java.util.UUID;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException(UUID value) {
        super(generateMessage(value));
    }
    public LessonNotFoundException(UUID moduleId, UUID lessonId) {
        super(generateMessage(moduleId, lessonId));
    }

    private static String generateMessage(UUID value) {
        return "Lesson not found with value: " + value;
    }
    private static String generateMessage(UUID moduleId, UUID lessonId) {
        return "Lesson not found with lessonId: " + lessonId + " and moduleId: " + moduleId;
    }
}
