package com.michal.socialnetworkingsite.service.Impl;

import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.entity.Post;
import com.michal.socialnetworkingsite.entity.User;
import com.michal.socialnetworkingsite.mapper.PostLikeMapper;
import com.michal.socialnetworkingsite.mapper.PostMapper;
import com.michal.socialnetworkingsite.mapper.UserMapper;
import com.michal.socialnetworkingsite.repository.PostLikeRepository;
import com.michal.socialnetworkingsite.repository.PostRepository;
import com.michal.socialnetworkingsite.repository.UserRepository;
import com.michal.socialnetworkingsite.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    @Override
    @Transactional
    public PostDto savePost(PostDto postDto) {

        User user = userRepository.findByUsername(postDto.getCreator());

        Post post = PostMapper.mapToPost(
                postDto,
                user);

        postRepository.save(post);

        return postDto;
    }

    @Override
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();

        return posts.stream().map(
                        p->PostMapper.mapToPostDto(p, p.getCreator().getUsername()))
                .toList().reversed();
    }

    @Override
    public PostDto getCurrentPost(Long postId) {

        Post post = postRepository.findById(postId).get();
        PostDto postDto = PostMapper.mapToPostDto(post, post.getCreator().getUsername());

        return postDto;
    }

    @Override
    public void updatePost(PostDto currentPost) {
        Post post = postRepository.findById(currentPost.getId()).get();
        post.setContent(currentPost.getContent());
        postRepository.save(post);
    }

    @Override
    @Transactional
    public List<PostDto> getFollowingPosts(String currentUsername) {

        User currentUser = userRepository.findByUsername(currentUsername);
        List<User> followingUsers = new ArrayList<>(currentUser.getFollowing());

        followingUsers.add(currentUser);

        List<PostDto> followingPosts = followingUsers.stream().map(
                author -> postRepository.findByCreatorUsername(author.getUsername()).stream()
                        .map(p->PostMapper.mapToPostDto(p, p.getCreator().getUsername())).toList()).flatMap(Collection::stream).sorted(Comparator.comparing(PostDto::getLastUpdated))
        .toList().reversed();

        return followingPosts;

    }

    @Override
    public List<PostDto> getRelatedPosts(Long userId) {

        User currentUser = userRepository.findById(userId).get();

        List<PostDto> followingPosts = currentUser.getPosts()
                .stream().map(
                        p->PostMapper.mapToPostDto(p, p.getCreator().getUsername()))
                .sorted(Comparator.comparing(PostDto::getLastUpdated)).toList()
                .reversed();

        return followingPosts;
    }

    @Override
    public void deletePost(Long postId) {

        postRepository.deleteById(postId);

    }
}
