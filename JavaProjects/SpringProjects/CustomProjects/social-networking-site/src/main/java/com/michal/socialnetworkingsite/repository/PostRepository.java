package com.michal.socialnetworkingsite.repository;

import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCreatorUsername(String username);

}
