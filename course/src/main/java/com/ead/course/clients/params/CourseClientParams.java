package com.ead.course.clients.params;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CourseClientParams {
    private UUID courseId;
    private int page;
    private int size;
    private String sort;
}
