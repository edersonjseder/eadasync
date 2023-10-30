package com.ead.authuser.publishers;

import com.ead.authuser.dtos.PasswordEventDto;
import com.ead.authuser.dtos.UserEventDto;
import com.ead.authuser.enums.ActionType;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEventPublisher {
    @Value("${ead.broker.exchange.passwordEvent}")
    private String exchangePasswordEvent;

    private final RabbitTemplate rabbitTemplate;

    public void publishPasswordEvent(PasswordEventDto passwordEventDto, ActionType actionType) {
        passwordEventDto.setActionType(actionType.name());
        rabbitTemplate.convertAndSend(exchangePasswordEvent, "", passwordEventDto);
    }
}
