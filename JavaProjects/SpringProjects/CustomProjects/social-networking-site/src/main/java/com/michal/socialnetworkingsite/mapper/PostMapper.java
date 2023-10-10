package com.michal.socialnetworkingsite.mapper;

import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.dto.PostLikeDto;
import com.michal.socialnetworkingsite.entity.Post;
import com.michal.socialnetworkingsite.entity.PostLike;
import com.michal.socialnetworkingsite.entity.User;
import com.michal.socialnetworkingsite.service.UserService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PostMapper {

    public static Post mapToPost(PostDto postDto, User user){
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setCreator(user);

        return post;

    }

    public static PostDto mapToPostDto(Post post, String username){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = post.getLastUpdated();
        postDto.setCreated(dateTime.format(formatter));

        postDto.setComments(post.getComments().stream().map(
                comment -> CommentMapper.mapToCommentDto(comment, comment.getCreator().getUsername())).toList());

        postDto.setLikes(post.getLikes().stream().map(
                postLike -> PostLikeMapper.mapToPostLikeDto(
                        postLike.getCreator().getUsername())).toList());

        postDto.setCreator(username);


        return postDto;}
}
