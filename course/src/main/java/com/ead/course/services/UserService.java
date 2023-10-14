package com.ead.course.services;

import com.ead.course.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
   private UserRepository userRepository;
}
