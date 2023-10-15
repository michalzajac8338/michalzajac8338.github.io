package com.michal.socialnetworkingsite.repository;

import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.entity.Post;
import com.michal.socialnetworkingsite.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCreatorUsername(String username);
    Page<Post> findByCreator(User user, Pageable pageable);
}
