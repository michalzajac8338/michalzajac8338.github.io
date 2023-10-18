package com.michal.socialnetworkingsite.repository;

import com.michal.socialnetworkingsite.entity.Comment;
import com.michal.socialnetworkingsite.entity.CommentLike;
import com.michal.socialnetworkingsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCreatorAndComment(User user, Comment comment);
}
