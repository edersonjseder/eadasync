package com.ead.configserver.services;

import com.ead.configserver.models.UserModel;
import com.ead.configserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User could not be found"));
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    @Transactional
    public void createUser(UserModel user) {
        var localUser = userRepository.findUserByUsername(user.getUsername());

        if (localUser.isPresent()){
            log.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
        } else {
            userRepository.save(user);
        }
    }
}
