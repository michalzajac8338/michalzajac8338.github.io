package com.michal.socialnetworkingsite.service.Impl;

import com.michal.socialnetworkingsite.dto.CommentDto;
import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.entity.Post;
import com.michal.socialnetworkingsite.entity.User;
import com.michal.socialnetworkingsite.exception.ResourceNotFoundException;
import com.michal.socialnetworkingsite.mapper.PostMapper;
import com.michal.socialnetworkingsite.repository.PostRepository;
import com.michal.socialnetworkingsite.repository.UserRepository;
import com.michal.socialnetworkingsite.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
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
    public void create(PostDto postDto) {

        User user = userRepository.findByUsername(postDto.getCreator())
                .orElseThrow(ResourceNotFoundException::new);

        Post post = PostMapper.mapToPost(
                postDto,
                user);

        postRepository.save(post);
    }

    @Override
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();

        return posts.stream().map(
                        p->PostMapper.mapToPostDto(p, p.getCreator().getUsername())).toList().reversed();
    }

    @Override
    public List<Object> getCurrentPost(Long postId, int page) {

        Post post = postRepository.findById(postId).orElseThrow(ResourceNotFoundException::new);
        PostDto postDto = PostMapper.mapToPostDto(post, post.getCreator().getUsername());

        int size = 3;
        int totalPages = (int) Math.ceil(((double) post.getComments().size() / size));

        List<CommentDto> commentsSorted = postDto.getComments().stream()
                .sorted(Comparator.comparing(CommentDto::getLastUpdated, Comparator.reverseOrder())).toList();

        // paging comments
        List<CommentDto> commentsReturned;
        try {
            commentsReturned = commentsSorted.subList((page-1)*size, page*size);
        } catch (IndexOutOfBoundsException e) {
            commentsReturned = commentsSorted.subList((page-1)*size, postDto.getComments().size());
        }

        List<Object> postCommentsPageAndPages = new ArrayList<>();
        postCommentsPageAndPages.add(postDto);
        postCommentsPageAndPages.add(commentsReturned);
        postCommentsPageAndPages.add(totalPages);

        return postCommentsPageAndPages;
    }

    @Override
    @Transactional
    public void updatePost(PostDto currentPost) {
        Post post = postRepository.findById(currentPost.getId()).orElseThrow(ResourceNotFoundException::new);
        post.setContent(currentPost.getContent());
        postRepository.save(post);
    }

    @Override
    @Transactional
    public Page<PostDto> getFollowingPosts(String currentUsername, Pageable pageable) {

        User currentUser = userRepository.findByUsername(currentUsername).orElseThrow(ResourceNotFoundException::new);
        List<User> followingUsers = new ArrayList<>(currentUser.getFollowing());

        if(!followingUsers.contains(currentUser)) {
            followingUsers.add(currentUser);
        }

        List<PostDto> followingPosts = followingUsers.stream().map(
                        author -> postRepository.findByCreatorUsername(author.getUsername()).stream()
                                .map(p->PostMapper.mapToPostDto(p, p.getCreator().getUsername())).toList())
                .flatMap(Collection::stream).sorted(Comparator.comparing(PostDto::getLastUpdated, Comparator.reverseOrder()))
                .toList();

        // posts pagination from list
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), followingPosts.size());
        List<PostDto> pageContent = followingPosts.subList(start, end);

        return new PageImpl<>(pageContent, pageable, followingPosts.size());
    }

    @Override
    @Transactional
    public List<Object> getRelatedPosts(Long userId, Pageable pageable) {

        User profileOwner = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
        Page<Post> userPostPage = postRepository.findByCreator(profileOwner, pageable);

        List<PostDto> followingPostsPage = userPostPage.stream().map(
                p->PostMapper.mapToPostDto(p, p.getCreator().getUsername())).toList();

        PageImpl<PostDto> page = new PageImpl<>(followingPostsPage);
        List<Object> pageAndNumberOfPages = new ArrayList<>();
        pageAndNumberOfPages.add(page);
        pageAndNumberOfPages.add(userPostPage.getTotalPages()-1);

        return pageAndNumberOfPages;
    }

    @Override
    public void deletePost(Long postId) {

        postRepository.deleteById(postId);

    }
}
