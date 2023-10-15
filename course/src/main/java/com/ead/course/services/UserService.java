package com.ead.course.services;

import com.ead.course.dtos.UserDto;
import com.ead.course.models.User;
import com.ead.course.repositories.UserRepository;
import com.ead.course.utils.UserUtils;
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
   private final UserUtils userUtils;

   public Page<UserDto> findAllUsers(Specification<User> spec, Pageable pageable) {
      return userUtils.toListUserDto(userRepository.findAll(spec, pageable));
   }

    public User saveUpdateUser(User user) {
        return userRepository.save(user);
    }

    public void removeUser(UUID id) {
        userRepository.deleteById(id);
    }
}
