package com.michal.socialnetworkingsite.service;

import com.michal.socialnetworkingsite.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto savePost(PostDto postDto);

    List<PostDto> getAllPosts();
}
