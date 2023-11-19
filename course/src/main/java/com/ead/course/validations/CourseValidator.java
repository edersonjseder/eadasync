package com.ead.course.validations;

import com.ead.course.dtos.CourseDto;
import com.ead.course.enums.UserType;
import com.ead.course.exceptions.UserNotFoundException;
import com.ead.course.security.AuthorizationCurrentUserService;
import com.ead.course.services.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
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
    private final AuthorizationCurrentUserService authorizationCurrentUserService;

    public CourseValidator(@Qualifier("defaultValidator") Validator validator, UserService userService,
                           AuthorizationCurrentUserService authorizationCurrentUserService) {
        this.validator = validator;
        this.userService = userService;
        this.authorizationCurrentUserService = authorizationCurrentUserService;
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
        var currentUserId = authorizationCurrentUserService.getCurrentUser().getId();

        if (currentUserId.equals(userInstructor)) {
            var user = userService.fetchUserById(userInstructor);

            if (user == null) {
                throw new UserNotFoundException(COURSE_USER_NOT_FOUND_MENSAGEM + userInstructor);
            }

            if (user.getUserType().equals(UserType.STUDENT.name())) {
                errors.rejectValue("userInstructor", "UserInstructorError", "Usuário deve ser INSTRUCTOR ou ADMIN.");
            }
        } else {
            throw new AccessDeniedException(authorizationCurrentUserService.getCurrentUser().getUsername() + " doesn't have the correct authorities to perform this action");
        }
    }
}
