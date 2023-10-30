package com.ead.emailsending.service;

import com.ead.emailsending.model.Email;
import com.ead.emailsending.utils.ConfigProperties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class SendEmailService {

    @Value("${mail.smtp.username}")
    private String username;
    @Value("${mail.smtp.password}")
    private String password;
    private final ConfigProperties configProperties;

    @Async
    public void sendEmail(Email emailModel) throws MessagingException, UnsupportedEncodingException {
        Properties properties = configProperties.getProperties();
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        session.setDebug(true);

        Address[] toUser = InternetAddress.parse(emailModel.getSendTo());

        //create a MimeMessage object
        Message message = new MimeMessage(session);
        //set From email field
        message.setFrom(new InternetAddress(username, "[EAD Team]", "UTF-8"));
        //set To email field
        message.setRecipients(Message.RecipientType.TO, toUser);
        //set email subject field
        message.setSubject(emailModel.getSubject());
        //set the content of the email message
        message.setContent(emailModel.getMessage(), "text/html; charset=utf-8");
        //send the email message
        Transport.send(message);
    }
}
