package com.michal.socialnetworkingsite.repository;

import com.michal.socialnetworkingsite.entity.Post;
import com.michal.socialnetworkingsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameOrEmail(String username, String email);
    User findByUsername(String username);
    List<User> findByFollowing(User user);
    List<User> findByUsernameContainsOrFirstNameContainsOrLastNameContains(String username, String firstName, String lastName);
    @Query(nativeQuery=true, value="SELECT * FROM user ORDER BY RAND() LIMIT 3")
    List<User> getThreeRandomUsers();

    User findByEmail(String email);
}
