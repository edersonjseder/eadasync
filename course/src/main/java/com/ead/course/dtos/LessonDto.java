package com.ead.course.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonDto extends RepresentationModel<LessonDto> {
    private UUID id;
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    @NotBlank(message = "Video URL is required")
    private String videoUrl;
    private String creationDate;
    private ModuleDto module;
}
