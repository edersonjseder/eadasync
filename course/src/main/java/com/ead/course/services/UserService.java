package com.ead.course.services;

import com.ead.course.dtos.UserDto;
import com.ead.course.models.User;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.UserRepository;
import com.ead.course.utils.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
   private final UserRepository userRepository;
   private final CourseRepository courseRepository;
   private final UserUtils userUtils;

   public Page<UserDto> findAllUsers(Specification<User> spec, Pageable pageable) {
      return userUtils.toListUserDto(userRepository.findAll(spec, pageable));
   }

   public User fetchUserById(UUID id) {
       return userRepository.findUserById(id);
   }

    public User saveUpdateUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void removeUser(UUID id) {
        courseRepository.deleteCourseUserByUser(id);
        userRepository.deleteById(id);
    }
}
