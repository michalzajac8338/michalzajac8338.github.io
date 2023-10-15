package com.michal.socialnetworkingsite.service;

import com.michal.socialnetworkingsite.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    void savePost(PostDto postDto);
    List<PostDto> getAllPosts();

    List<Object> getCurrentPost(Long postId, int page);

    void updatePost(PostDto currentPost);

    List<Object> getFollowingPosts(String currentUsername, int page);

    List<Object> getRelatedPosts(Long userId, Pageable pageable);

    void deletePost(Long postId);

}