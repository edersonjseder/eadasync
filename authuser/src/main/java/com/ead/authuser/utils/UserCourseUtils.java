package com.ead.authuser.utils;

import com.ead.authuser.dtos.UserCourseDto;
import com.ead.authuser.models.User;
import com.ead.authuser.models.UserCourse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCourseUtils {
    private final UserUtils userUtils;

    public UserCourse toUserCourse(UserCourseDto userCourseDto, User user) {
        return UserCourse.builder()
                .courseId(userCourseDto.getCourseId())
                .user(user)
                .build();
    }

    public UserCourseDto toUserCourseDto(UserCourse userCourse) {
        return UserCourseDto.builder()
                .id(userCourse.getId())
                .user(userUtils.toUserDto(userCourse.getUser()))
                .courseId(userCourse.getCourseId())
                .build();
    }
}
