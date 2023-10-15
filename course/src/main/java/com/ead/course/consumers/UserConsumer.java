package com.ead.course.consumers;

import com.ead.course.dtos.UserEventDto;
import com.ead.course.enums.ActionType;
import com.ead.course.services.UserService;
import com.ead.course.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConsumer {
    private final UserService userService;
    private final UserUtils userUtils;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.userEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.userEventExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true"))
    )
    public void listenUserEvent(@Payload UserEventDto userEventDto) {
        var user = userUtils.toUser(userEventDto);
        userService.saveUser(user, userEventDto.getActionType());
    }
}
