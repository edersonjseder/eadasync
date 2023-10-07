package com.ead.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCourseDto {
    private UUID id;
    private UUID userId;
    @NotNull(message = "ID is required")
    private UUID courseId;
    private UserDto user;
}
