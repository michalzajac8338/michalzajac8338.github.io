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

    @GetMapping
    public String commentAPost(@RequestParam Long postId,
                               Model model){

        PostDto currentPost = postService.getCurrentPost(postId);
        model.addAttribute("currentPost", currentPost);

        String currentUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("currentUserUsername", currentUserUsername);

        CommentDto commentDto = new CommentDto();
        commentDto.setUsername((SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("comment", commentDto);

        return "single-post";
    }

    @PostMapping("/comment/{postId}")
    public String submitComment(@ModelAttribute CommentDto commentDto,
                                     @PathVariable Long postId){

        commentDto.setUsername((SecurityContextHolder.getContext().getAuthentication().getName()));
        commentService.saveComment(commentDto, postId);

        return "redirect:/Z/news/singlePost?postId={postId}";
    }


    @PostMapping("/edit/{postId}")
    public String editPost(@PathVariable Long postId){

        return "redirect:/Z/news/singlePost?postId={postId}&edit=true";
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
    @PostMapping("/submitEdition/{postId}")
    public String submitPostEdition(@ModelAttribute PostDto currentPost,
                                    @PathVariable Long postId){

        currentPost.setId(postId);
        currentPost.setCreator((SecurityContextHolder.getContext().getAuthentication().getName()));
        postService.updatePost(currentPost);

        return "redirect:/Z/news/singlePost?postId={postId}";
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

    @PostMapping("/{postId}/deleteComment/{commentId}")
    public String deleteComment(@PathVariable Long postId,
                              @PathVariable Long commentId){

        commentService.deleteComment(commentId);

        return "redirect:/Z/news/singlePost?postId={postId}";
    }

}
