package com.ead.course.exceptions;

import java.util.UUID;

public class ModuleNotFoundException extends RuntimeException {
    public ModuleNotFoundException(UUID value) {
        super(generateMessage(value));
    }
    public ModuleNotFoundException(UUID courseId, UUID moduleId) {
        super(generateMessage(courseId, moduleId));
    }

    private static String generateMessage(UUID value) {
        return "Module not found with value: " + value;
    }
    private static String generateMessage(UUID courseId, UUID moduleId) {
        return "Module not found with courseId: " + courseId + " and moduleId: " + moduleId;
    }
}
