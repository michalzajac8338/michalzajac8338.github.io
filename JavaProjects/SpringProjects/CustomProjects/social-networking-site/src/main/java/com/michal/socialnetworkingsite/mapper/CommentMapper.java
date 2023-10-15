package com.michal.socialnetworkingsite.mapper;

import com.michal.socialnetworkingsite.dto.CommentDto;
import com.michal.socialnetworkingsite.dto.PostLikeDto;
import com.michal.socialnetworkingsite.entity.Comment;
import com.michal.socialnetworkingsite.entity.Post;
import com.michal.socialnetworkingsite.entity.PostLike;
import com.michal.socialnetworkingsite.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentMapper {

    public static Comment mapToComment(CommentDto commentDto, Post post, User user){

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setCreator(user);
        comment.setPost(post);

        return comment;

    }

    public static CommentDto mapToCommentDto(Comment comment, String username){

        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = comment.getLastUpdated();
        commentDto.setLastUpdated(dateTime.format(formatter));

        commentDto.setUsername(username);

        commentDto.setLikes(comment.getLikes().stream().map(
                commentLike -> CommentLikeMapper.mapToCommentLikeDto(
                        commentLike.getCreator().getUsername()
                )
        ).toList()
        );

        return commentDto;
    }

}
