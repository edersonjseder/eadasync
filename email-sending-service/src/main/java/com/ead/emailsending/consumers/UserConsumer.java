package com.ead.emailsending.consumers;

import com.ead.emailsending.dtos.PasswordEventDto;
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
            value = @Queue(value = "${ead.broker.queue.userEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.userEventExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true"))
    )
    public void listenUserEvent(@Payload PasswordEventDto passwordEventDto) {
        try {
            sendEmailService.sendEmail(emailUtils.formatEmail(passwordEventDto));
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.getStackTrace();
        }
    }
}
