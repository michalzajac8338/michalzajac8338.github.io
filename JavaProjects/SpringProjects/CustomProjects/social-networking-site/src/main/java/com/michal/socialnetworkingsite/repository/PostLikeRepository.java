package com.michal.socialnetworkingsite.repository;

import com.michal.socialnetworkingsite.entity.Post;
import com.michal.socialnetworkingsite.entity.PostLike;
import com.michal.socialnetworkingsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByCreatorAndPost(User user, Post post);
}
