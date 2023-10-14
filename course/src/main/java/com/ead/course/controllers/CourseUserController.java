package com.ead.course.controllers;

import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{id}/users")
    public ResponseEntity<String> getAllUsersByCourse(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                             @PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok("");
    }

    @PostMapping("/{id}/users/subscribe")
    public ResponseEntity<SubscriptionDto> subscribeUserInCourse(@PathVariable(value = "id") UUID id,
                                                         @RequestBody @Valid SubscriptionDto subscriptionDto) {
        // Verificacoes state transfer
        return ResponseEntity.status(CREATED).body(SubscriptionDto.builder().build());
    }
}
