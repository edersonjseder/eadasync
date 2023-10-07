package com.ead.authuser.dtos;

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
public class CourseDto extends RepresentationModel<CourseDto> {
    private UUID id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    private String imageUrl;
    private String creationDate;
    private String lastUpdateDate;
    @NotBlank(message = "Status is required")
    private String courseStatus;
    @NotBlank(message = "Level is required")
    private String courseLevel;
    @NotNull(message = "Instructor is required")
    private UUID userInstructor;
}
