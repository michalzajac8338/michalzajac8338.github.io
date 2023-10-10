package com.michal.socialnetworkingsite.controller;

import com.michal.socialnetworkingsite.dto.CommentDto;
import com.michal.socialnetworkingsite.dto.CommentLikeDto;
import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.dto.PostLikeDto;
import com.michal.socialnetworkingsite.service.CommentLikeService;
import com.michal.socialnetworkingsite.service.CommentService;
import com.michal.socialnetworkingsite.service.PostLikeService;
import com.michal.socialnetworkingsite.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Z/news/singlePost")
@AllArgsConstructor
public class SinglePostController {

    PostLikeService postLikeService;
    PostService postService;
    CommentService commentService;
    CommentLikeService commentLikeService;

    @PostMapping("/like/{postId}")
    public String likeCurrentPost(@PathVariable Long postId){

        PostLikeDto postLikeDto = new PostLikeDto();
        postLikeDto.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        postLikeDto.setPostId(postId);

        postLikeService.savePostLike(postLikeDto);

        return "redirect:/Z/news/singlePost?postId={postId}";
    }

    @PostMapping("/comment/{postId}")
    public String commentCurrentPost(@ModelAttribute CommentDto commentDto,
                                     @PathVariable Long postId){

        commentDto.setUsername((SecurityContextHolder.getContext().getAuthentication().getName()));
        commentService.saveComment(commentDto, postId);

        return "redirect:/Z/news/singlePost?postId={postId}";
    }

    @PostMapping("/{postId}")
    public String comment(@PathVariable Long postId){

        return "redirect:/Z/news/singlePost?postId={postId}";
    }

    @GetMapping
    public String commentAPost(@RequestParam Long postId,
                               Model model){

        PostDto currentPost = postService.getCurrentPost(postId);
        model.addAttribute("currentPost", currentPost);

        CommentDto commentDto = new CommentDto();
        commentDto.setUsername((SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("comment", commentDto);

        return "single-post";
    }
    @PostMapping("/edit/{postId}")
    public String editPost(@PathVariable Long postId){

        return "redirect:/Z/news/singlePost?postId={postId}&edit=true";
    }

    @PostMapping("/submitEdition/{postId}")
    public String submitPostEdition(@ModelAttribute PostDto currentPost,
                                    @PathVariable Long postId){

        currentPost.setId(postId);
        currentPost.setCreator((SecurityContextHolder.getContext().getAuthentication().getName()));
        postService.updatePost(currentPost);

        return "redirect:/Z/news/singlePost?postId={postId}";
    }

    @GetMapping("/edit")
    public String editAPost(@RequestParam Long postId,
                            Model model){

        PostDto currentPost = postService.getCurrentPost(postId);
        model.addAttribute("currentPost", currentPost);

        CommentDto commentDto = new CommentDto();
        commentDto.setUsername((SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("comment", commentDto);

        return "single-post";
    }

    @PostMapping("{postId}/likeComment/{commentId}")
    public String likeAPost(@PathVariable Long postId,
            @PathVariable Long commentId){

        CommentLikeDto commentLikeDto = new CommentLikeDto();
        commentLikeDto.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        commentLikeDto.setCommentId(commentId);

        commentLikeService.saveCommentLike(commentLikeDto);

        return "redirect:/Z/news/singlePost?postId={postId}";
    }

}
