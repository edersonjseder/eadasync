package com.ead.configserver.utils;

import com.ead.configserver.enums.Roles;
import com.ead.configserver.models.UserModel;
import com.ead.configserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {
    @Value("${ead.config-server.username}")
    private String username;
    @Value("${ead.config-server.password}")
    private String password;
    @Value("${ead.config-server.authority}")
    private String authority;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    /**
     * Creates a user with basic attributes set for test.
     *
     * @return The User object.
     */
    public void createAdminUser() {
        userService.createUser(UserModel.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .authority(Roles.valueOf(authority))
                .build());
    }
}
