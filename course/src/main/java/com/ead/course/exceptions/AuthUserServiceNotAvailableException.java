package com.ead.course.exceptions;

public class AuthUserServiceNotAvailableException extends RuntimeException {
    public AuthUserServiceNotAvailableException(String message) {
        super(message);
    }
}
