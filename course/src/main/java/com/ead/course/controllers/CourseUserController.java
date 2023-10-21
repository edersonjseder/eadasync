package com.ead.course.controllers;

import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.services.CourseService;
import com.ead.course.services.UserService;
import com.ead.course.specifications.SpecificationTemplate;
import com.ead.course.specifications.UserSpec;
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
public class CourseUserController {
    private final UserService userService;
    private final CourseService courseService;

    @GetMapping("/{id}/users")
    public ResponseEntity<Page<UserDto>> getAllUsersByCourse(UserSpec spec,
                                                             @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                             @PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(userService.findAllUsers(SpecificationTemplate.userCourseId(id).and(spec), pageable));
    }

    @PostMapping("/{id}/users/subscribe")
    public ResponseEntity<String> subscribeUserInCourse(@PathVariable(value = "id") UUID id,
                                                         @RequestBody @Valid SubscriptionDto subscriptionDto) {
        return ResponseEntity.status(CREATED).body(courseService.saveSubscriptionUserInCourse(id, subscriptionDto));
    }
}
