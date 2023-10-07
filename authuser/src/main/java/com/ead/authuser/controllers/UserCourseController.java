package com.ead.authuser.controllers;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.UserCourseDto;
import com.ead.authuser.services.UserCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class UserCourseController {
    private final CourseClient courseClient;
    private final UserCourseService userCourseService;

    @GetMapping("/users/{id}/courses")
    public ResponseEntity<Page<CourseDto>> getAllCoursesByUser(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                               @PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(courseClient.getAllCoursesByUser(id, pageable));
    }

    @PostMapping("/users/{id}/courses/subscribe")
    public ResponseEntity<UserCourseDto> subscribeUserInCourse(@PathVariable(value = "id") UUID id,
                                                               @RequestBody @Valid UserCourseDto userCourseDto) {
        return ResponseEntity.status(CREATED).body(userCourseService.saveSubscription(id, userCourseDto));
    }

    @DeleteMapping("/users/courses/{courseId}")
    public ResponseEntity<String> deleteUserCourseByCourse(@PathVariable("courseId") UUID courseId) {
        userCourseService.removeUserCourseByCourse(courseId);
        return ResponseEntity.ok("UserCourse deleted successfully");
    }
}
