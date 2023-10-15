package com.michal.socialnetworkingsite.controller;

import com.michal.socialnetworkingsite.dto.PostDto;
import com.michal.socialnetworkingsite.dto.PostLikeDto;
import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.service.PostLikeService;
import com.michal.socialnetworkingsite.service.PostService;
import com.michal.socialnetworkingsite.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getProfile(@PathVariable Long userId){

        return "redirect:/Z/viewProfile/{userId}?page=0";
    }
    @GetMapping("/viewProfile/{userId}")
    public String viewProfile(@PathVariable Long userId,
                              @RequestParam int page,
                              @PageableDefault(sort = "lastUpdated", direction = Sort.Direction.DESC, value = 2)
                              Pageable pageable,
                              Model model){

        List<Object> userPostsAndTotalPages = postService.getRelatedPosts(userId, pageable);
        model.addAttribute("posts", userPostsAndTotalPages.get(0));

        UserDto userDto = userService.getUser(userId);
        model.addAttribute("user", userDto);

        UserDto currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);

        List<UserDto> users = userService.getRandomUsers();
        model.addAttribute("users", users);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPostsAndTotalPages.get(1));

        return "view-profile";
    }
    // Update
    @PostMapping("/editProfile")
    public String editProfile(){

        return "redirect:/Z/editProfile";
    }

    @GetMapping("/editProfile")
    public String editUser(Model model){

        UserDto currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);

        return "edit-profile";
    }

    @PostMapping("/editProfile/save/{userId}")
    public String savedEditedProfile(@ModelAttribute("user") UserDto userDto,
                                     @PathVariable Long userId){

        userDto.setId(userId);
        userService.updateUser(userDto);
        return "redirect:/Z/news?page=1";
    }

    // Delete
    @PostMapping("/deleteProfile")
    public String deleteUser(){

        userService.deleteCurrentUser();

        return "redirect:/Z/login?deletedSuccessfully";
    }

    //Additional functionality
    @GetMapping("/login")
    public String loginUser(Model model){

        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);

        return "user-login";
    }

    @PostMapping("/follow/{userId}")
    public String follow(HttpServletRequest request,
                         @PathVariable Long userId){

        userService.followOrUnfollowUser(userId);
        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

    @PostMapping("/like/{postId}")
    public String likeAPost(@PathVariable Long postId,
                            HttpServletRequest request){

        PostLikeDto postLikeDto = new PostLikeDto();

        UserDto currentUser = userService.getCurrentUser();
        postLikeDto.setUsername(currentUser.getUsername());
        postLikeDto.setPostId(postId);

        postLikeService.savePostLike(postLikeDto);
        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }
    @PostMapping("/searchUser")
    public String searchForUser(HttpServletRequest request,
                                RedirectAttributes attributes,
                                @RequestParam String searchValue) {

        List<UserDto> results = userService.getUsers(searchValue);
        attributes.addFlashAttribute("searchResults", results);
        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

    @PostMapping("/cancel")
    public String cancel(HttpServletRequest request){

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/editProfile/cancel")
    public String cancelProfileEdition(){

        return "redirect:/Z/news?page=1";
    }
}
