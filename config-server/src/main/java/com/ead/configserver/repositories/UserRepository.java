package com.ead.configserver.repositories;

import com.ead.configserver.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findUserByUsername(String username);
}
