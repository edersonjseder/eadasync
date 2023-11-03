package com.ead.emailsending.consumers;

import com.ead.emailsending.dtos.ChangePasswordEventDto;
import com.ead.emailsending.dtos.ForgotPasswordEventDto;
import com.ead.emailsending.service.SendEmailService;
import com.ead.emailsending.utils.EmailUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class UserConsumer {
    private final SendEmailService sendEmailService;
    private final EmailUtils emailUtils;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.changePasswordEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.changePasswordEventExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true"))
    )
    public void listenUserChangePasswordEvent(@Payload ChangePasswordEventDto changePasswordEventDto) {
        try {
            sendEmailService.sendEmail(emailUtils.formatEmail(changePasswordEventDto));
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.getStackTrace();
        }
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.forgotPasswordEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.forgotPasswordEventExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true"))
    )
    public void listenUserForgotPasswordEvent(@Payload ForgotPasswordEventDto forgotPasswordEventDto) {
        try {
            sendEmailService.sendEmail(emailUtils.formatForgotPasswordEmail(forgotPasswordEventDto));
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.getStackTrace();
        }
    }
}
