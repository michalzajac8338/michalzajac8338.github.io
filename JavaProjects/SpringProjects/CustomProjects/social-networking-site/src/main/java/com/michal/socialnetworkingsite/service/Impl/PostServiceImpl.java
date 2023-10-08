package com.michal.socialnetworkingsite.service.Impl;

import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.entity.Post;
import com.michal.socialnetworkingsite.entity.User;
import com.michal.socialnetworkingsite.mapper.PostLikeMapper;
import com.michal.socialnetworkingsite.mapper.PostMapper;
import com.michal.socialnetworkingsite.repository.PostLikeRepository;
import com.michal.socialnetworkingsite.repository.PostRepository;
import com.michal.socialnetworkingsite.repository.UserRepository;
import com.michal.socialnetworkingsite.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private PostLikeRepository postLikeRepository;

    @Override
    @Transactional
    public PostDto savePost(PostDto postDto) {

        User user = userRepository.findByUsername(postDto.getCreator());

        Post post = PostMapper.mapToPost(
                postDto,
                user,
                null);

        postRepository.save(post);

        return postDto;
    }

    @Override
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();

        return posts.stream().map(p->PostMapper.mapToPostDto(p,p.getCreator().getUsername(),
                p.getLikes().stream().map(
                        postLike -> PostLikeMapper.mapToPostLikeDto(
                        postLike.getCreator().getUsername())).toList()))
                .toList().reversed();
    }
}
