package com.michal.socialnetworkingsite.service.Impl;

import com.michal.socialnetworkingsite.dto.PostLikeDto;
import com.michal.socialnetworkingsite.entity.Post;
import com.michal.socialnetworkingsite.entity.PostLike;
import com.michal.socialnetworkingsite.entity.User;
import com.michal.socialnetworkingsite.exception.ResourceNotFoundException;
import com.michal.socialnetworkingsite.mapper.PostLikeMapper;
import com.michal.socialnetworkingsite.repository.PostLikeRepository;
import com.michal.socialnetworkingsite.repository.PostRepository;
import com.michal.socialnetworkingsite.repository.UserRepository;
import com.michal.socialnetworkingsite.service.PostLikeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    PostLikeRepository postLikeRepository;
    PostRepository postRepository;
    UserRepository userRepository;

    @Override
    @Transactional
    public void savePostLike(PostLikeDto postLikeDto) {

        User user = userRepository.findByUsername(postLikeDto.getUsername()).orElse(null);
        Post post = postRepository.findById(postLikeDto.getPostId()).orElse(null);

        PostLike postLikeCheck = postLikeRepository.findByCreatorAndPost(user,post).orElse(null);

        if(postLikeCheck!=null){
            postLikeRepository.delete(postLikeCheck);
        } else {
            PostLike postLike = PostLikeMapper.mapToPostLike(post, user);
            postLikeRepository.save(postLike);
        }
    }
}
