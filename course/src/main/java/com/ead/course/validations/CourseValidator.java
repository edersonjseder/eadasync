package com.ead.course.validations;

import com.ead.course.dtos.CourseDto;
import com.ead.course.enums.UserType;
import com.ead.course.exceptions.UserNotFoundException;
import com.ead.course.services.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.UUID;

import static com.ead.course.constants.CourseMessagesConstants.COURSE_USER_NOT_FOUND_MENSAGEM;

@Component
public class CourseValidator implements Validator {
    @Qualifier("defaultValidator")
    private final Validator validator;
    private final UserService userService;

    public CourseValidator(@Qualifier("defaultValidator") Validator validator, UserService userService) {
        this.validator = validator;
        this.userService = userService;
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
        var user = userService.fetchUserById(userInstructor);

        if (user == null) {
            throw new UserNotFoundException(COURSE_USER_NOT_FOUND_MENSAGEM + userInstructor);
        }

        if (user.getUserType().equals(UserType.STUDENT.name())) {
            errors.rejectValue("userInstructor", "UserInstructorError", "Usuário deve ser INSTRUCTOR ou ADMIN.");
        }
    }
}
