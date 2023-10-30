package com.ead.emailsending.utils;

import com.ead.emailsending.dtos.PasswordEventDto;
import com.ead.emailsending.model.Email;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
    private static final String SUBJECT = "Password changing";
    public Email formatEmail(PasswordEventDto passwordEventDto) {
        MailHtml mailHtml = new MailHtml();

        return Email.builder()
                .sentFrom(passwordEventDto.getUsername())
                .sendTo(passwordEventDto.getEmail())
                .subject(SUBJECT)
                .message(mailHtml.formatChangedPasswordHtml(passwordEventDto.getFullName(), passwordEventDto.getMessage()))
                .build();
    }
}
