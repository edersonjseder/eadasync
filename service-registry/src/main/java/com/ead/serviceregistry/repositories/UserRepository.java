package com.ead.serviceregistry.repositories;

import com.ead.serviceregistry.persistence.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findUserByUsername(String username);
}
