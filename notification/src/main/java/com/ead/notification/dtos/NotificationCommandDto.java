package com.ead.notification.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationCommandDto extends RepresentationModel<NotificationCommandDto> {
    private String title;
    private String message;
    private UUID userId;
}
