package com.michal.socialnetworkingsite.controller;

import com.michal.socialnetworkingsite.dto.CommentDto;
import com.michal.socialnetworkingsite.dto.CommentLikeDto;
import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

        // delete viewing post -> redirect to news
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
    public String submitComment(@ModelAttribute @Valid CommentDto commentDto,
                                BindingResult result,
                                @PathVariable Long postId,
                                HttpServletRequest request,
                                RedirectAttributes attributes){

        String referer = request.getHeader("Referer");

        // comment validation
        if(result.hasErrors()){
            attributes.addFlashAttribute("commentBlank", true);
            return "redirect:" + referer;
        }

        UserDto currentUser = userService.getCurrentUser();
        commentDto.setUsername(currentUser.getUsername());
        commentService.saveComment(commentDto, postId);

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
    public String submitCommentEdition(@ModelAttribute @Valid CommentDto currentComment,
                                       BindingResult result,
                                       @PathVariable Long commentId,
                                       HttpServletRequest request,
                                       RedirectAttributes attributes){

        String referer = request.getHeader("Referer");

        // back to editing when invalid comment
        if(result.hasErrors()) {
            attributes.addFlashAttribute("editComment", true);
            CommentDto commentDto = commentService.getCurrentComment(commentId);
            attributes.addFlashAttribute("currentComment", commentDto);
            attributes.addFlashAttribute("commentBlank", true);
            return "redirect:" + referer;
        }


        currentComment.setId(commentId);
        commentService.updateComment(currentComment.getId(), currentComment.getContent());

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
