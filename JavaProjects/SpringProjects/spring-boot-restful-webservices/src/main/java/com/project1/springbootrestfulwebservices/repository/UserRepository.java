package com.project1.springbootrestfulwebservices.repository;

import com.project1.springbootrestfulwebservices.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// no need @Repository <= extends
public interface UserRepository extends JpaRepository<User, Long> {
}
