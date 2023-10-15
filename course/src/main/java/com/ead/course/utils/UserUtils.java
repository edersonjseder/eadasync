package com.ead.course.utils;

import com.ead.course.dtos.CourseDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.dtos.UserEventDto;
import com.ead.course.models.Course;
import com.ead.course.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {
    public Page<UserDto> toListUserDto(Page<User> users) {
        return users.map(user -> UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .userStatus(user.getUserStatus())
                .userType(user.getUserType())
                .build());
    }

    public User toUser(UserEventDto userEventDto) {
        var user = new User();
        BeanUtils.copyProperties(userEventDto, user);
        return user;
    }
}
