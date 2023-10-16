package com.michal.socialnetworkingsite.controller;

import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.service.PostService;
import com.michal.socialnetworkingsite.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
                       @RequestParam int page,
                       @PageableDefault(value = 5) Pageable pageable){

        PostDto postDto = new PostDto();
        model.addAttribute("post", postDto);

        UserDto currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);

        List<UserDto> users = userService.getRandomUsers();
        model.addAttribute("users", users);

        Page<PostDto> followingPostsPage = postService.getFollowingPosts(currentUser.getUsername(), pageable);
        model.addAttribute("posts", followingPostsPage);

        model.addAttribute("totalPages", followingPostsPage.getTotalPages());
        model.addAttribute("pageNr", page);
        return "news";
    }

    // CRUD for posts
    // Create
    @PostMapping("/post")
    public String postAPost(@ModelAttribute("post") @Valid PostDto postDto,
                            BindingResult result,
                            RedirectAttributes attributes){

        if(result.hasErrors()){
            attributes.addFlashAttribute("postBlank", true);
            return "redirect:/Z/news?page=0";
        }

        UserDto currentUser = userService.getCurrentUser();
        postDto.setCreator(currentUser.getUsername());
        postService.savePost(postDto);

        return "redirect:/Z/news?page=0";
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
