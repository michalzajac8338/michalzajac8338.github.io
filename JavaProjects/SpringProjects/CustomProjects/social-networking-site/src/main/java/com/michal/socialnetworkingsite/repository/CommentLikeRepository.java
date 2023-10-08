package com.michal.socialnetworkingsite.repository;

import com.michal.socialnetworkingsite.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
