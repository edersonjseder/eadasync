package com.ead.course.validations;

import com.ead.course.clients.AuthUserClientFeign;
import com.ead.course.dtos.CourseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.UUID;

@Component
public class CourseValidator implements Validator {
    @Qualifier("defaultValidator")
    private final Validator validator;
    private final AuthUserClientFeign authUserClientFeign;

    public CourseValidator(@Qualifier("defaultValidator") Validator validator, AuthUserClientFeign authUserClientFeign) {
        this.validator = validator;
        this.authUserClientFeign = authUserClientFeign;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        var courseDto = (CourseDto) target;
        validator.validate(courseDto, errors);

        if (!errors.hasErrors()) {
            validateUserInstructor(courseDto.getUserInstructor(), errors);
        }
    }

    private void validateUserInstructor(UUID userInstructor, Errors errors) {
        var user = authUserClientFeign.getUserById(userInstructor);

        if (user.getType().equals("STUDENT")) {
            errors.rejectValue("userInstructor", "UserInstructorError", "Usuário deve ser INSTRUCTOR ou ADMIN.");
        }
    }
}
