package com.kce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kce.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email); // For Spring Security
    Optional<User> findByUniqueId(String uniqueId);
}
