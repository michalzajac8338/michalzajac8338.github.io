package com.michal.socialnetworkingsite.repository;

import com.michal.socialnetworkingsite.entity.Comment;
import com.michal.socialnetworkingsite.entity.CommentLike;
import com.michal.socialnetworkingsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    CommentLike findByCreatorAndComment(User user, Comment comment);
}
