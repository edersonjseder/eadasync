package com.ead.authuser.services;

import com.ead.authuser.dtos.UserCourseDto;
import com.ead.authuser.exceptions.UserCourseException;
import com.ead.authuser.exceptions.UserCourseNotFoundException;
import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.utils.UserCourseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.ead.authuser.constants.UserMessagesConstants.USUARIO_EXISTENTE_MENSAGEM;

@Service
@RequiredArgsConstructor
public class UserCourseService {
    private final UserCourseRepository userCourseRepository;
    private final UserService userService;
    private final UserCourseUtils userCourseUtils;

    public UserCourseDto saveSubscription(UUID userId, UserCourseDto userCourseDto) {
        var user = userService.findUserById(userId);

        if (userCourseRepository.existsByUserAndCourseId(user, userCourseDto.getCourseId())) {
            throw new UserCourseException(USUARIO_EXISTENTE_MENSAGEM);
        }

        return userCourseUtils.toUserCourseDto(userCourseRepository.save(userCourseUtils.toUserCourse(userCourseDto, user)));
    }

    @Transactional
    public void removeUserCourseByCourse(UUID courseId) {
        if (!userCourseRepository.existsByCourseId(courseId)) {
            throw new UserCourseNotFoundException(courseId);
        }
        userCourseRepository.deleteAllByCourseId(courseId);
    }
}
