package com.michal.socialnetworkingsite.controller;

import com.michal.socialnetworkingsite.dto.CommentDto;
import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.dto.PostLikeDto;
import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.service.PostLikeService;
import com.michal.socialnetworkingsite.service.PostService;
import com.michal.socialnetworkingsite.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Z/news")
@AllArgsConstructor
public class NewsController {

    private UserService userService;
    private PostService postService;
    private PostLikeService postLikeService;

    // Home
    @GetMapping
    public String news(Model model){

        PostDto postDto = new PostDto();
        model.addAttribute("post", postDto);

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto currentUser = userService.getCurrentUser(currentUsername);
        model.addAttribute("currentUser", currentUser);

//        List<PostDto> posts = postService.getAllPosts();
        List<PostDto> followingPosts = postService.getFollowingPosts(currentUsername);
        model.addAttribute("posts", followingPosts);

        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "news";
    }

    // CRUD for posts
    // Create
    @PostMapping("/post")
    public String postAPost(@ModelAttribute("post") PostDto postDto){

        postDto.setCreator((SecurityContextHolder.getContext().getAuthentication().getName()));
        postService.savePost(postDto);

        return "redirect:/Z/news?success";
    }

    // Read
    @PostMapping("/singlePost/{postId}")
    public String comment(@PathVariable Long postId){

        return "redirect:/Z/news/singlePost?postId={postId}";
    }

    // Update
    @PostMapping("/singlePost/edit/{postId}")
    public String editPost(@PathVariable Long postId){

        return "redirect:/Z/news/singlePost?postId={postId}&edit=true";
    }
    @GetMapping("/singlePost/edit")
    public String editAPost(@RequestParam Long postId,
                            Model model){

        PostDto currentPost = postService.getCurrentPost(postId);
        model.addAttribute("currentPost", currentPost);

        CommentDto commentDto = new CommentDto();
        commentDto.setUsername((SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("comment", commentDto);

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto currentUser = userService.getCurrentUser(currentUsername);
        model.addAttribute("currentUser", currentUser);

        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "single-post";
    }
    @PostMapping("/singlePost/submitEdition/{postId}")
    public String submitPostEdition(@ModelAttribute PostDto currentPost,
                                    @PathVariable Long postId){

        currentPost.setId(postId);
        currentPost.setCreator((SecurityContextHolder.getContext().getAuthentication().getName()));
        postService.updatePost(currentPost);


        return "redirect:/Z/news/singlePost?postId={postId}";
    }

    // Delete
    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId){

        postService.deletePost(postId);

        return "redirect:/Z/news?postDeleted=true";
    }

    // Additional functionality
    @PostMapping("/like/{postId}")
    public String likeAPost(@PathVariable Long postId){

        PostLikeDto postLikeDto = new PostLikeDto();
        postLikeDto.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        postLikeDto.setPostId(postId);

        postLikeService.savePostLike(postLikeDto);

        return "redirect:/Z/news";
    }


}
