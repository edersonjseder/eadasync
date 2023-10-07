package com.ead.course.controllers;

import com.ead.course.dtos.CourseDto;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.CourseSpec;
import com.ead.course.specifications.SpecificationTemplate;
import com.ead.course.utils.CourseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final CourseUtils courseUtils;

    @GetMapping(value = "/all")
    public ResponseEntity<Page<CourseDto>> getAllCourses(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                         CourseSpec spec, @RequestParam(required = false) UUID userId) {
        Page<CourseDto> coursePage;

        if (userId != null) {
            coursePage = courseService.findAllCourses(SpecificationTemplate.courseUserId(userId).and(spec), pageable);
        } else {
            coursePage = courseService.findAllCourses(spec, pageable);
        }

        // Hateoas link creation snippet
        if (!coursePage.isEmpty()) {
            coursePage.toList().forEach((course) -> {
                course.add(linkTo(methodOn(CourseController.class).getCourseById(course.getId())).withSelfRel());
            });
        }

        return ResponseEntity.ok(coursePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(courseUtils.toCourseDto(courseService.getOneCourse(id)));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<CourseDto> registerCourse(@RequestBody CourseDto courseDto, Errors errors) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(courseDto, errors));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<CourseDto> updateCourse(@RequestBody CourseDto courseDto, Errors errors) {
        return ResponseEntity.ok(courseService.saveCourse(courseDto, errors));
    }

    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity<String> removeCourse(@PathVariable("id") UUID id) {
        courseService.removeCourse(id);
        return ResponseEntity.ok("Course successfully removed.");
    }
}
