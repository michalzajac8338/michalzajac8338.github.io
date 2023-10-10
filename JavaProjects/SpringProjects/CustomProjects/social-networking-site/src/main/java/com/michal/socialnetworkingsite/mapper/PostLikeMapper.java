package com.michal.socialnetworkingsite.mapper;

import com.michal.socialnetworkingsite.dto.PostLikeDto;
import com.michal.socialnetworkingsite.entity.Post;
import com.michal.socialnetworkingsite.entity.PostLike;
import com.michal.socialnetworkingsite.entity.User;

public class PostLikeMapper {

    public static PostLike mapToPostLike(Post post, User user){

        PostLike postLike = new PostLike();
        postLike.setCreator(user);
        postLike.setPost(post);

        return postLike;
    }

    public static PostLikeDto mapToPostLikeDto(String username){

        PostLikeDto postLikeDto = new PostLikeDto();
        postLikeDto.setUsername(username);

        return postLikeDto;
    }

}
