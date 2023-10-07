package com.ead.course.controllers;

import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.services.CourseUserService;
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
    private final CourseUserService courseUserService;

    @GetMapping("/{id}/users")
    public ResponseEntity<Page<UserDto>> getAllUsersByCourse(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                             @PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(courseUserService.getUsersByCourse(id, pageable));
    }

    @PostMapping("/{id}/users/subscribe")
    public ResponseEntity<SubscriptionDto> subscribeUserInCourse(@PathVariable(value = "id") UUID id,
                                                         @RequestBody @Valid SubscriptionDto subscriptionDto) {
        return ResponseEntity.status(CREATED).body(courseUserService.saveAndSendSubscriptionUserInCourse(id, subscriptionDto));
    }

    @DeleteMapping("/courses/users/{userId}")
    public ResponseEntity<String> deleteCourseUserByUser(@PathVariable(value = "userId") UUID userId) {
        courseUserService.removeCourseUserByUser(userId);
        return ResponseEntity.ok("CourseUser deleted successfully");
    }
}
