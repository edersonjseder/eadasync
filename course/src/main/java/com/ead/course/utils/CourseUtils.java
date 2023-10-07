package com.ead.course.utils;

import com.ead.course.dtos.CourseDto;
import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseUtils {
    private final DateUtils dateUtils;

    public CourseDto toCourseDto(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .imageUrl(course.getImageUrl())
                .courseStatus(course.getCourseStatus().name())
                .courseLevel(course.getCourseLevel().name())
                .creationDate(dateUtils.parseDate(course.getCreationDate()))
                .lastUpdateDate(dateUtils.parseDate(course.getLastUpdateDate()))
                .userInstructor(course.getUserInstructor())
                .build();
    }

    public Page<CourseDto> toListCourseDto(Page<Course> courses) {
        return courses.map(course -> CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .imageUrl(course.getImageUrl())
                .courseStatus(course.getCourseStatus().name())
                .courseLevel(course.getCourseLevel().name())
                .creationDate(dateUtils.parseDate(course.getCreationDate()))
                .lastUpdateDate(dateUtils.parseDate(course.getLastUpdateDate()))
                .userInstructor(course.getUserInstructor())
                .build());
    }
}
