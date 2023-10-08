package com.michal.socialnetworkingsite.repository;

import com.michal.socialnetworkingsite.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
