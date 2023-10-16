package com.michal.socialnetworkingsite.controller;

import com.michal.socialnetworkingsite.dto.*;
import com.michal.socialnetworkingsite.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;
import java.util.Map;

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
    @GetMapping("/postId={postId}")
    public String commentAPost(HttpServletRequest request,
                               RedirectAttributes attributes,
                               @PathVariable Long postId,
                               @RequestParam int page,
                               Model model){

        // delete->redirect to news
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            try{
            boolean deleted = (boolean) inputFlashMap.get("postDeleted");
            if(deleted) {
                attributes.addFlashAttribute("postDeleted", true);
                return "redirect:/Z/news?page=0";
            }
            } catch (NullPointerException ignored){}
        }

        // otherwise
        List<Object> postCommentsPageAndPages  = postService.getCurrentPost(postId, page);
        PostDto currentPost = (PostDto) postCommentsPageAndPages.get(0);

        List<CommentDto> commentsPage = (List<CommentDto>) postCommentsPageAndPages.get(1);
        currentPost.setComments(commentsPage);
        model.addAttribute("currentPost", currentPost);

        UserDto currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);

        CommentDto commentDto = new CommentDto();
        commentDto.setUsername(currentUser.getUsername());
        model.addAttribute("comment", commentDto);

        List<UserDto> users = userService.getRandomUsers();
        model.addAttribute("users", users);

        model.addAttribute("totalPages", postCommentsPageAndPages.get(2));
        model.addAttribute("pageNr", page);

        return "single-post";
    }

    @PostMapping("/comment/{postId}")
    public String submitComment(HttpServletRequest request,
                                @ModelAttribute CommentDto commentDto,
                                @PathVariable Long postId){

        UserDto currentUser = userService.getCurrentUser();
        commentDto.setUsername(currentUser.getUsername());
        commentService.saveComment(commentDto, postId);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    // Read - everywhere :)
    // Update
    @PostMapping("/{postId}/editComment/{commentId}")
    public String editComment(@PathVariable Long commentId,
                              HttpServletRequest request,
                              RedirectAttributes attributes){

        attributes.addFlashAttribute("editComment", true);
        CommentDto commentDto = commentService.getCurrentComment(commentId);
        attributes.addFlashAttribute("currentComment", commentDto);
        String referer = request.getHeader("Referer");

        return "redirect:" + referer;

    }

    @PostMapping("submitEdition/{postId}/comment/{commentId}")
    public String submitCommentEdition(HttpServletRequest request,
                                       @ModelAttribute CommentDto currentComment,
                                       @PathVariable Long commentId){

        currentComment.setId(commentId);
        commentService.updateComment(currentComment.getId(), currentComment.getContent());

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    // Delete
    @PostMapping("/{postId}/deleteComment/{commentId}")
    public String deleteComment(HttpServletRequest request,
                                @PathVariable Long commentId){

        commentService.deleteComment(commentId);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    // Additional functionality
    @PostMapping("{postId}/likeComment/{commentId}")
    public String likeComment(HttpServletRequest request,
                              @PathVariable Long commentId){

        UserDto currentUser = userService.getCurrentUser();

        CommentLikeDto commentLikeDto = new CommentLikeDto();
        commentLikeDto.setUsername(currentUser.getUsername());
        commentLikeDto.setCommentId(commentId);

        commentLikeService.saveCommentLike(commentLikeDto);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
