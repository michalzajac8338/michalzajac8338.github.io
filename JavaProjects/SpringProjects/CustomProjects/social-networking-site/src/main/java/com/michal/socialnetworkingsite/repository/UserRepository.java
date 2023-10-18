package com.michal.socialnetworkingsite.repository;

import com.michal.socialnetworkingsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    List<User> findByFollowing(User user);
    List<User> findByUsernameContainsOrFirstNameContainsOrLastNameContains(String username, String firstName, String lastName);
    @Query(nativeQuery=true, value="SELECT * FROM user ORDER BY RAND() LIMIT 3")
    List<User> getThreeRandomUsers();
    Optional<User> findByEmail(String email);
}
