package com.ead.notification.dtos;

import com.ead.notification.enums.NotificationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDto extends RepresentationModel<NotificationDto> {
    @NotNull(message = "ID is required")
    private UUID id;
    private String title;
    private String message;
    @NotNull(message = "Status is required")
    private NotificationStatus status;
    private UUID userId;
}
