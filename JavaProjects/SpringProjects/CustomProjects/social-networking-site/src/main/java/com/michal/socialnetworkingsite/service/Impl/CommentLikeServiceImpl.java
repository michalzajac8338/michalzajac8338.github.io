package com.michal.socialnetworkingsite.service.Impl;

import com.michal.socialnetworkingsite.dto.CommentLikeDto;
import com.michal.socialnetworkingsite.entity.*;
import com.michal.socialnetworkingsite.exception.ResourceNotFoundException;
import com.michal.socialnetworkingsite.mapper.CommentLikeMapper;
import com.michal.socialnetworkingsite.repository.CommentLikeRepository;
import com.michal.socialnetworkingsite.repository.CommentRepository;
import com.michal.socialnetworkingsite.repository.UserRepository;
import com.michal.socialnetworkingsite.service.CommentLikeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {

    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private CommentLikeRepository commentLikeRepository;

    @Override
    @Transactional
    public void saveCommentLike(CommentLikeDto commentLikeDto) {

        User user = userRepository.findByUsername(commentLikeDto.getUsername()).orElse(null);
        Comment comment = commentRepository.findById(commentLikeDto.getCommentId()).orElse(null);
        CommentLike commentLikeCheck = commentLikeRepository.findByCreatorAndComment(user, comment).orElse(null);

        if(commentLikeCheck!=null){
            commentLikeRepository.delete(commentLikeCheck);
        } else {
            CommentLike commentLike = CommentLikeMapper.mapToCommentLike(comment, user);
            commentLikeRepository.save(commentLike);
        }
    }
}