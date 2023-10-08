package com.michal.socialnetworkingsite.repository;

import com.michal.socialnetworkingsite.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
