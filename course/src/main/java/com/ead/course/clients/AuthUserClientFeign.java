package com.ead.course.clients;

import com.ead.course.clients.params.CourseClientParams;
import com.ead.course.configs.FeignConfig;
import com.ead.course.dtos.UserCourseDto;
import com.ead.course.dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "${ead.auth-user.url}", configuration = FeignConfig.class)
public interface AuthUserClientFeign {
    @GetMapping(path = "/users/all")
    Page<UserDto> getUsersByCourse(@SpringQueryMap CourseClientParams params);

    @GetMapping(path = "/users/{id}")
    UserDto getUserById(@PathVariable("id") UUID id);

    @PostMapping("/users/{id}/courses/subscribe")
    Object postSubscriptionUserInCourse(@PathVariable("id") UUID id, UserCourseDto userCourseDto);

    @DeleteMapping(path = "/users/courses/{courseId}")
    void removeCourseInAuthUser(@PathVariable("courseId") UUID courseId);
}
