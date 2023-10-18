package com.michal.socialnetworkingsite.service;

import com.michal.socialnetworkingsite.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    void create(PostDto postDto);
    List<Object> getCurrentPost(Long postId, int page);
    List<PostDto> getAllPosts();
    void updatePost(PostDto currentPost);
    void deletePost(Long postId);
    Page<PostDto> getFollowingPosts(String currentUsername, Pageable pageable);
    List<Object> getRelatedPosts(Long userId, Pageable pageable);
}
