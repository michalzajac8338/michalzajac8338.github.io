package com.michal.socialnetworkingsite.controller;

import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.entity.User;
import com.michal.socialnetworkingsite.repository.UserRepository;
import com.michal.socialnetworkingsite.service.PostService;
import com.michal.socialnetworkingsite.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/Z/news")
@AllArgsConstructor
public class PostController {

    private UserService userService;
    private PostService postService;

    @GetMapping
    public String news(Model model){

        PostDto postDto = new PostDto();
        UserDto userDto = new UserDto();

        List<PostDto> posts = postService.getAllPosts();

        model.addAttribute("user", userDto);
        model.addAttribute("post", postDto);
        model.addAttribute("posts", posts);

        return "news";
    }

    @PostMapping("/post")
    public String postAPost(@ModelAttribute("post") PostDto postDto,
                            Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getCurrentUser(auth.getName());
        postDto.setCreator(user);

        postService.savePost(postDto);

        model.addAttribute("post", postDto);

        return "redirect:/Z/news?success";
    }


}
