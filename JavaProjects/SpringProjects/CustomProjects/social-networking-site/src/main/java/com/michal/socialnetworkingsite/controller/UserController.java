package com.michal.socialnetworkingsite.controller;

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
@RequestMapping("Z")
@AllArgsConstructor
public class UserController {

    private PostService postService;
    private UserService userService;
    private PostLikeService postLikeService;

    // Home
    @GetMapping
    public String home(){
        return "home-page";
    }

    // CRUD for users
    // Create
    @GetMapping("/register")
    public String registerUser(Model model){

        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);

        return "register-form";
    }

    @PostMapping("/register/save")
    public String registerUser(@ModelAttribute("user") UserDto userDto){

        userService.save(userDto);

        return "redirect:/Z/login?registered_successfully";
    }

    // Read
    @PostMapping("/viewProfile/{userId}")
    public String getProfile(@PathVariable Long userId,
                             Model model){

        UserDto userDto = userService.getUser(userId);
        model.addAttribute("user", userDto);

        return "redirect:/Z/viewProfile/{userId}";
    }
    @GetMapping("/viewProfile/{userId}")
    public String viewProfile(@PathVariable Long userId,
                              Model model){

        List<PostDto> userPosts = postService.getRelatedPosts(userId);
        model.addAttribute("posts", userPosts);

        UserDto userDto = userService.getUser(userId);
        model.addAttribute("user", userDto);

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto currentUser = userService.getCurrentUser(currentUserName);
        model.addAttribute("currentUser", currentUser);

        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "view-profile";
    }
    // Update
    @PostMapping("/editProfile")
    public String editProfile(){

        return "redirect:/Z/editProfile";
    }

    @GetMapping("/editProfile")
    public String editUser(Model model){

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.getCurrentUser(currentUserName);
        model.addAttribute("user", userDto);

        return "edit-profile";
    }

    @PostMapping("/editProfile/save/{userId}")
    public String savedEditedProfile(@ModelAttribute("user") UserDto userDto,
                                     @PathVariable Long userId){

        userDto.setId(userId);
        userService.updateUser(userDto);
        return "redirect:/Z/news";
    }

    // Delete
    @PostMapping("/deleteProfile")
    public String deleteUser(Model model){

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.deleteProfile(currentUserName);

        return "redirect:/Z/login?deletedSuccessfully";
    }

    //Additional functionality
    @GetMapping("/login")
    public String loginUser(Model model){

        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);

        return "user-login";
    }

    @PostMapping("/follow/{username}")
    public String follow(@PathVariable String username){

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        userService.followOrUnfollowUser(currentUserName, username);
        return "redirect:/Z/news";

    }

    @PostMapping("viewProfile/{userId}/like/{postId}")
    public String likeAPostViewingProfile(@PathVariable Long userId,
                                          @PathVariable Long postId){

        PostLikeDto postLikeDto = new PostLikeDto();
        postLikeDto.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        postLikeDto.setPostId(postId);

        postLikeService.savePostLike(postLikeDto);

        return "redirect:/Z/viewProfile/{userId}";
    }
}
