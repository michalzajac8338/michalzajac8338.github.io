package com.michal.socialnetworkingsite.repository;

import com.michal.socialnetworkingsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameOrEmail(String username, String username1);

    User findByUsername(String name);
}
