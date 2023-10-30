package com.ead.authuser.encoder;

public interface PasswordEncoder {
    String hashPassword(String password);
}
