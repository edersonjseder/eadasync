package com.ead.serviceregistry.utils;

import com.ead.serviceregistry.enums.Roles;
import com.ead.serviceregistry.persistence.UserModel;
import com.ead.serviceregistry.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {
    @Value("${ead.service-registry.username}")
    private String username;
    @Value("${ead.service-registry.password}")
    private String password;
    @Value("${ead.service-registry.authority}")
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
