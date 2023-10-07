package com.ead.course.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionDto {
    private UUID userCourseId;
    @NotNull(message = "ID value is required")
    private UUID userId;
    private UserDto user;
    private CourseDto course;
}
