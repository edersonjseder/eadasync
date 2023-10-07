package com.ead.authuser.controllers;

import com.ead.authuser.dtos.InstructorDto;
import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructors")
@RequiredArgsConstructor
public class InstructorUserController {
    private final UserService userService;

    @PostMapping(value = "/subscribe")
    public ResponseEntity<UserDto> subscribeInstructor(@RequestBody @Valid InstructorDto instructorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.subscribeInstructor(instructorDto));
    }
}
