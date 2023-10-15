package com.ead.course.services;

import com.ead.course.dtos.UserDto;
import com.ead.course.enums.ActionType;
import com.ead.course.exceptions.UserException;
import com.ead.course.models.User;
import com.ead.course.repositories.UserRepository;
import com.ead.course.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.ead.course.constants.CourseMessagesConstants.COURSE_USER_MENSAGEM;

@Service
@RequiredArgsConstructor
public class UserService {
   private final UserRepository userRepository;
   private final UserUtils userUtils;

   public Page<UserDto> findAllUsers(Specification<User> spec, Pageable pageable) {
      return userUtils.toListUserDto(userRepository.findAll(spec, pageable));
   }

    public User saveUser(User user, String actionType) {
        switch (ActionType.valueOf(actionType)) {
            case CREATE -> {
                return userRepository.save(user);
            }
            default -> throw new UserException(COURSE_USER_MENSAGEM);
        }
    }
}
