package com.michal.socialnetworkingsite.controller;

import com.michal.socialnetworkingsite.dto.*;
import com.michal.socialnetworkingsite.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Z/news/singlePost")
@AllArgsConstructor
public class SinglePostController {

    PostLikeService postLikeService;
    PostService postService;
    CommentService commentService;
    CommentLikeService commentLikeService;
    UserService userService;

    // CRUD for comments
    // Create
    @GetMapping
    public String commentAPost(@RequestParam Long postId,
                               Model model){

        PostDto currentPost = postService.getCurrentPost(postId);
        model.addAttribute("currentPost", currentPost);

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto currentUser = userService.getCurrentUser(currentUsername);
        model.addAttribute("currentUser", currentUser);

        CommentDto commentDto = new CommentDto();
        commentDto.setUsername((SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("comment", commentDto);

        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "single-post";
    }

    @PostMapping("/comment/{postId}")
    public String submitComment(@ModelAttribute CommentDto commentDto,
                                @PathVariable Long postId){

        commentDto.setUsername((SecurityContextHolder.getContext().getAuthentication().getName()));
        commentService.saveComment(commentDto, postId);

        return "redirect:/Z/news/singlePost?postId={postId}";
    }

    // Read - everywhere :)
    // Update
    @PostMapping("/{postId}/editComment/{commentId}")
    public String editComment(@PathVariable Long postId,
                              @PathVariable Long commentId){

        return "redirect:/Z/news/singlePost/{postId}/comment?commentId={commentId}&editComment=true";
    }

    @GetMapping("/{postId}/comment")
    public String editAComment(@PathVariable Long postId,
                               @RequestParam Long commentId,
                               Model model){

        PostDto currentPost = postService.getCurrentPost(postId);
        model.addAttribute("currentPost", currentPost);

        CommentDto commentDto = commentService.getCurrentComment(commentId);
        model.addAttribute("currentComment", commentDto);

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto currentUser = userService.getCurrentUser(currentUsername);
        model.addAttribute("currentUser", currentUser);

        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "single-post";
    }
    @PostMapping("submitEdition/{postId}/comment/{commentId}")
    public String submitCommentEdition(@ModelAttribute CommentDto currentComment,
                                       @PathVariable Long commentId,
                                       @PathVariable Long postId){

        currentComment.setId(commentId);
        commentService.updateComment(currentComment.getId(), currentComment.getContent());

        return "redirect:/Z/news/singlePost?postId={postId}";
    }

    // Delete
    @PostMapping("/{postId}/deleteComment/{commentId}")
    public String deleteComment(@PathVariable Long postId,
                                @PathVariable Long commentId){

        commentService.deleteComment(commentId);

        return "redirect:/Z/news/singlePost?postId={postId}";
    }

    // Additional functionality
    @PostMapping("/like/{postId}")
    public String likeCurrentPost(@PathVariable Long postId){

        PostLikeDto postLikeDto = new PostLikeDto();
        postLikeDto.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        postLikeDto.setPostId(postId);

        postLikeService.savePostLike(postLikeDto);

        return "redirect:/Z/news/singlePost?postId={postId}";
    }

    @PostMapping("{postId}/likeComment/{commentId}")
    public String likeComment(@PathVariable Long postId,
                              @PathVariable Long commentId){

        CommentLikeDto commentLikeDto = new CommentLikeDto();
        commentLikeDto.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        commentLikeDto.setCommentId(commentId);

        commentLikeService.saveCommentLike(commentLikeDto);

        return "redirect:/Z/news/singlePost?postId={postId}";
    }
}
