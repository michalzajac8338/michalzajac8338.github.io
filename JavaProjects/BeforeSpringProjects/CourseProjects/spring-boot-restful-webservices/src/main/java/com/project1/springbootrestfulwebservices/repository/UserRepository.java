package com.project1.springbootrestfulwebservices.repository;

import com.project1.springbootrestfulwebservices.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// no need @Repository <= extends
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
