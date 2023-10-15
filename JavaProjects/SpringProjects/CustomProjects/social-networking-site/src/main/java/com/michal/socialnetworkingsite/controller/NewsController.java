package com.michal.socialnetworkingsite.controller;

import com.michal.socialnetworkingsite.dto.CommentDto;
import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.service.PostService;
import com.michal.socialnetworkingsite.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/Z/news")
@AllArgsConstructor
public class NewsController {

    private UserService userService;
    private PostService postService;


    // Home
    @GetMapping
    public String news(Model model,
                       @RequestParam int page){

        PostDto postDto = new PostDto();
        model.addAttribute("post", postDto);

        UserDto currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);

        List<UserDto> users = userService.getRandomUsers();
        model.addAttribute("users", users);

        List<Object> followingPostsAndSize = postService.getFollowingPosts(currentUser.getUsername(), page);
        List<PostDto> followingPosts = (List<PostDto>) followingPostsAndSize.get(0);
        model.addAttribute("posts", followingPosts);

        int totalPages = (int) followingPostsAndSize.get(1);

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNr", page);
        return "news";
    }

    // CRUD for posts
    // Create
    @PostMapping("/post")
    public String postAPost(@ModelAttribute("post") PostDto postDto){

        UserDto currentUser = userService.getCurrentUser();
        postDto.setCreator(currentUser.getUsername());
        postService.savePost(postDto);

        return "redirect:/Z/news?page=1&success";
    }

    // Read
    @PostMapping("/singlePost/{postId}")
    public String comment(@PathVariable Long postId){

        return "redirect:/Z/news/singlePost/postId={postId}?page=1";
    }

    // Update
    @PostMapping("/singlePost/edit/{postId}")
    public String editPost(RedirectAttributes attributes,
                           @PathVariable Long postId){

        attributes.addFlashAttribute("editPost", true);
        return "redirect:/Z/news/singlePost/postId={postId}?page=1";
    }

    @PostMapping("/singlePost/submitEdition/{postId}")
    public String submitPostEdition(HttpServletRequest request,
                                    @ModelAttribute PostDto currentPost,
                                    @PathVariable Long postId){

        UserDto currentUser = userService.getCurrentUser();
        currentPost.setId(postId);
        currentPost.setCreator(currentUser.getUsername());
        postService.updatePost(currentPost);

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

    // Delete
    @PostMapping("/delete/{postId}")
    public String deletePost(HttpServletRequest request,
                             RedirectAttributes attributes,
                             @PathVariable Long postId){

        postService.deletePost(postId);
        attributes.addFlashAttribute("postDeleted", true);

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;

    }
}
