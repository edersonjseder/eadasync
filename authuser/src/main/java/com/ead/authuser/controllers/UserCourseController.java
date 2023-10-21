package com.ead.authuser.controllers;

import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserCourseController {
    private final UserService userService;

    @GetMapping("/users/{id}/courses")
    public ResponseEntity<Page<CourseDto>> getAllCoursesByUser(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                               @PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(userService.getCoursesByUser(id, pageable));
    }
}
