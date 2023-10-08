package com.michal.socialnetworkingsite.service;

import com.michal.socialnetworkingsite.dto.PostLikeDto;
import com.michal.socialnetworkingsite.entity.PostLike;

public interface PostLikeService {

    PostLikeDto findByPostId(Long id);

    PostLikeDto savePostLike(PostLikeDto postLikeDto);
}
