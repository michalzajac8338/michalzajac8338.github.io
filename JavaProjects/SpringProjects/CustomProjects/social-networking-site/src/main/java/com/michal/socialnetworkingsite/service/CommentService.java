package com.michal.socialnetworkingsite.service;

import com.michal.socialnetworkingsite.dto.CommentDto;

public interface CommentService {
    void saveComment(CommentDto commentDto, Long postId);
    CommentDto getCurrentComment(Long commentId);
    void updateComment(Long commentId, String content);
    void deleteComment(Long commentId);
}
