package com.michal.socialnetworkingsite.service.Impl;

import com.michal.socialnetworkingsite.dto.CommentDto;
import com.michal.socialnetworkingsite.entity.Comment;
import com.michal.socialnetworkingsite.entity.Post;
import com.michal.socialnetworkingsite.entity.User;
import com.michal.socialnetworkingsite.exception.ResourceNotFoundException;
import com.michal.socialnetworkingsite.mapper.CommentMapper;
import com.michal.socialnetworkingsite.repository.CommentRepository;
import com.michal.socialnetworkingsite.repository.PostRepository;
import com.michal.socialnetworkingsite.repository.UserRepository;
import com.michal.socialnetworkingsite.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    UserRepository userRepository;
    PostRepository postRepository;
    CommentRepository commentRepository;

    @Override
    @Transactional
    public void saveComment(CommentDto commentDto, Long postId) {

        User user = userRepository.findByUsername(commentDto.getUsername()).orElseThrow(ResourceNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(ResourceNotFoundException::new);
        Comment comment = CommentMapper.mapToComment(commentDto, post, user);
        commentRepository.save(comment);
    }

    @Override
    public CommentDto getCurrentComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(ResourceNotFoundException::new);
        return CommentMapper.mapToCommentDto(comment, comment.getCreator().getUsername());
    }

    @Override
    @Transactional
    public void updateComment(Long commentId, String content) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(ResourceNotFoundException::new);
        comment.setContent(content);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {

        commentRepository.deleteById(commentId);
    }
}
