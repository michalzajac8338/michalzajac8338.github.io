package com.michal.socialnetworkingsite.service.Impl;

import com.michal.socialnetworkingsite.dto.CommentDto;
import com.michal.socialnetworkingsite.entity.Comment;
import com.michal.socialnetworkingsite.entity.Post;
import com.michal.socialnetworkingsite.entity.User;
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

    @Transactional
    @Override
    public void saveComment(CommentDto commentDto, Long postId) {

        User user = userRepository.findByUsername(commentDto.getUsername());
        Post post = postRepository.findById(postId).get();
        Comment comment = CommentMapper.mapToComment(commentDto, post, user);
        commentRepository.save(comment);

    }
}
