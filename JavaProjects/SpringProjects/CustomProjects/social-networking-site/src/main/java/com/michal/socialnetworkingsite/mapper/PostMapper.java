package com.michal.socialnetworkingsite.mapper;

import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.entity.Post;

public class PostMapper {

    public static Post mapToPost(PostDto postDto){
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setCreator(postDto.getCreator());
        post.setComments(postDto.getComments());
        post.setLikes(postDto.getLikes());

        return post;

    }

    public static PostDto mapToPostDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setContent(post.getContent());
        postDto.setCreator(post.getCreator());
        postDto.setComments(post.getComments());
        postDto.setLikes(post.getLikes());

        return postDto;}
}
