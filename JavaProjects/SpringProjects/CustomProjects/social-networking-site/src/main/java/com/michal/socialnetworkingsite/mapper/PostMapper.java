package com.michal.socialnetworkingsite.mapper;

import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.dto.PostLikeDto;
import com.michal.socialnetworkingsite.entity.Post;
import com.michal.socialnetworkingsite.entity.PostLike;
import com.michal.socialnetworkingsite.entity.User;
import com.michal.socialnetworkingsite.service.UserService;
import lombok.AllArgsConstructor;

import java.util.List;

public class PostMapper {

    public static Post mapToPost(PostDto postDto, User user, List<PostLike> postLikes){
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setCreator(user);
        post.setComments(postDto.getComments());
        post.setLikes(postLikes);

        return post;

    }

    public static PostDto mapToPostDto(Post post, String username, List<PostLikeDto> postLikesDto){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setComments(post.getComments());
        postDto.setLikes(postLikesDto);
        postDto.setCreator(username);

        return postDto;}
}
