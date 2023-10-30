package com.ead.authuser.encoder.impl;

import com.ead.authuser.encoder.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderImpl implements PasswordEncoder {
    @Override
    public String hashPassword(String password) {
        var encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
