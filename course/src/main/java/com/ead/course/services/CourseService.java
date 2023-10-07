package com.ead.course.services;

import com.ead.course.clients.AuthUserClientFeign;
import com.ead.course.dtos.CourseDto;
import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.exceptions.CourseException;
import com.ead.course.exceptions.CourseNotFoundException;
import com.ead.course.models.Course;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.utils.CourseUtils;
import com.ead.course.validations.CourseValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ead.course.constants.CourseMessagesConstants.COURSE_NAME_EXISTENTE_MENSAGEM;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseUserRepository courseUserRepository;
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final CourseUtils courseUtils;
    private final CourseValidator courseValidator;
    private final AuthUserClientFeign authUserClientFeign;

    public Page<CourseDto> findAllCourses(Specification<Course> spec, Pageable pageable) {
        return courseUtils.toListCourseDto(courseRepository.findAll(spec, pageable));
    }

    public Course getOneCourse(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    @Transactional
    public CourseDto saveCourse(CourseDto courseDto, Errors errors) {
        Course course;

        courseValidator.validate(courseDto, errors);

        if (errors.hasErrors()) {
            log.error(errors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", ")));
            throw new CourseException(errors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", ")));
        }

        if (courseDto.getId() == null) {
            if (courseRepository.existsCourseByName(courseDto.getName())) {
                throw new CourseException(COURSE_NAME_EXISTENTE_MENSAGEM + courseDto.getName());
            }

            course = new Course();

            BeanUtils.copyProperties(courseDto, course);

            course.setCourseStatus(CourseStatus.valueOf(courseDto.getCourseStatus()));
            course.setCourseLevel(CourseLevel.valueOf(courseDto.getCourseLevel()));
            course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));

        } else {
            course = courseRepository.findById(courseDto.getId()).orElseThrow(() -> new CourseNotFoundException(courseDto.getId()));

            course.setName(courseDto.getName());
            course.setDescription(courseDto.getDescription());
            course.setImageUrl(courseDto.getImageUrl());
            course.setCourseStatus(CourseStatus.valueOf(courseDto.getCourseStatus()));
            course.setCourseLevel(CourseLevel.valueOf(courseDto.getCourseLevel()));
        }

        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return courseUtils.toCourseDto(courseRepository.save(course));
    }

    @Transactional
    public void removeCourse(UUID id) {
        boolean delCourseUserInAuthUser = false;
        var course = courseRepository.findById(id);

        if (course.isPresent()) {
            var modules = moduleRepository.findAllModulesIntoCourse(id);
            if (!modules.isEmpty()) {
                modules.forEach(module -> {
                    var lessons = lessonRepository.findAllLessonsIntoModule(module.getId());
                    if (!lessons.isEmpty()) {
                        lessonRepository.deleteAll(lessons);
                    }
                });
                moduleRepository.deleteAll(modules);
            }
            var courseUserLst = courseUserRepository.findAllCourseUserIntoCourse(id);
            if (!courseUserLst.isEmpty()) {
                courseUserRepository.deleteAll(courseUserLst);
                delCourseUserInAuthUser = true;
            }
            courseRepository.deleteById(id);

            if (delCourseUserInAuthUser) {
                authUserClientFeign.removeCourseInAuthUser(id);
            }
        } else {
            throw new CourseNotFoundException(id);
        }
    }
}
