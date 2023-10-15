package com.michal.socialnetworkingsite.mapper;

import com.michal.socialnetworkingsite.dto.CommentLikeDto;
import com.michal.socialnetworkingsite.entity.*;

public class CommentLikeMapper {

    public static CommentLike mapToCommentLike(Comment comment, User user){

        CommentLike commentLike = new CommentLike();
        commentLike.setCreator(user);
        commentLike.setComment(comment);

        return commentLike;
    }

    public static CommentLikeDto mapToCommentLikeDto(String username){

        CommentLikeDto commentLikeDto = new CommentLikeDto();
        commentLikeDto.setUsername(username);

        return commentLikeDto;
    }
}
