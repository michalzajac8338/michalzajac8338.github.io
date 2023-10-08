package com.michal.socialnetworkingsite.controller;

import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.dto.PostLikeDto;
import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.service.PostLikeService;
import com.michal.socialnetworkingsite.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Z/news")
@AllArgsConstructor
public class PostController {

    private PostService postService;
    private PostLikeService postLikeService;

    @GetMapping
    public String news(Model model){

        PostDto postDto = new PostDto();

        List<PostDto> posts = postService.getAllPosts();

        model.addAttribute("post", postDto);
        model.addAttribute("posts", posts);

        return "news";
    }

    @PostMapping("/post")
    public String postAPost(@ModelAttribute("post") PostDto postDto){

        postDto.setCreator((SecurityContextHolder.getContext().getAuthentication().getName()));
        PostDto savedPost = postService.savePost(postDto);

        return "redirect:/Z/news?success";
    }

    @PostMapping("/like/{likeId}")
    public String likeAPost(@PathVariable Long likeId){

        PostLikeDto postLikeDto = new PostLikeDto();
        postLikeDto.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        postLikeDto.setPostId(likeId);

        postLikeService.savePostLike(postLikeDto);

        return "redirect:/Z/news";
    }


}
