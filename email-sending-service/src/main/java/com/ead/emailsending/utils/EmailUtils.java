package com.ead.emailsending.utils;

import com.ead.emailsending.dtos.ChangePasswordEventDto;
import com.ead.emailsending.dtos.ForgotPasswordEventDto;
import com.ead.emailsending.model.Email;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
    private static final String SUBJECT = "Reset password link";
    public Email formatEmail(ChangePasswordEventDto changePasswordEventDto) {
        MailHtml mailHtml = new MailHtml();

        return Email.builder()
                .sentFrom(changePasswordEventDto.getUsername())
                .sendTo(changePasswordEventDto.getEmail())
                .subject(SUBJECT)
                .message(mailHtml.formatChangedPasswordHtml(changePasswordEventDto.getFullName(), changePasswordEventDto.getMessage()))
                .build();
    }

    public Email formatForgotPasswordEmail(ForgotPasswordEventDto forgotPasswordEventDto) {
        MailHtml mailHtml = new MailHtml();

        return Email.builder()
                .sentFrom(forgotPasswordEventDto.getEmailFrom())
                .sendTo(forgotPasswordEventDto.getEmailTo())
                .subject(SUBJECT)
                .message(mailHtml.formatEmail(forgotPasswordEventDto.getLink(), forgotPasswordEventDto.getFullName()))
                .build();
    }
}
