package com.ead.authuser.services;

import com.ead.authuser.dtos.ResetPasswordDto;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.exceptions.PasswordException;
import com.ead.authuser.exceptions.PasswordsNotMatchException;
import com.ead.authuser.exceptions.UserNotFoundException;
import com.ead.authuser.publishers.PasswordEventPublisher;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.responses.PasswordResponse;
import com.ead.authuser.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ChangeUserPasswordService {
    private final UserUtils userUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PasswordEventPublisher passwordEventPublisher;

    @Transactional
    public PasswordResponse updateUserPassword(ResetPasswordDto resetPasswordDto) {
        var user = userRepository.findUserByEmail(resetPasswordDto.getEmail()).orElseThrow(() -> new UserNotFoundException(resetPasswordDto.getEmail()));

        if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword())) {
            throw new PasswordsNotMatchException();
        }

        if (!passwordEncoder.matches(resetPasswordDto.getOldPassword(), user.getPassword())) {
            throw new PasswordException();
        }

        var hashedPassword = passwordEncoder.encode(resetPasswordDto.getPassword());

        user.setPassword(hashedPassword);
        user.setCurrentPasswordDate(LocalDateTime.now(ZoneId.of("UTC")));
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        passwordEventPublisher.publishPasswordEvent(userUtils.toPasswordEventDto(user), ActionType.SEND);

        return userUtils.toPasswordResponse(user);
    }
}
