package com.ead.course.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String value) {
        super(generateMessage(value));
    }
    private static String generateMessage(String value) {
        return value;
    }
}
