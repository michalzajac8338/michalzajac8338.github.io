package com.michal.socialnetworkingsite.controller;

import com.michal.socialnetworkingsite.dto.PostLikeDto;
import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.service.PostLikeService;
import com.michal.socialnetworkingsite.service.PostService;
import com.michal.socialnetworkingsite.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("Z")
@AllArgsConstructor
public class UserController {

    private PostService postService;
    private UserService userService;
    private PostLikeService postLikeService;

    // Home
    @GetMapping
    public String home(Model model){

        // home page for logged-in user
        try {
            UserDto currentUser = userService.getCurrentUser();
            model.addAttribute("currentUser", currentUser);
            List<UserDto> users = userService.getRandomUsers();
            model.addAttribute("users", users);
        } catch (NullPointerException ignored){}

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
    public String registerUser(Model model,
                               @ModelAttribute("user") @Valid UserDto userDto,
                               BindingResult result){

        // data validation before saving to db
        UserDto existingUser = userService.findUserByEmail(userDto.getEmail());
        if(existingUser!=null){
            result.rejectValue("email", null, "email already in database");
        }

        existingUser = userService.findUserByUsername(userDto.getUsername());
        if(existingUser!=null){
            result.rejectValue("username", null, "username already in database");
        }

        if(userDto.getPassword().length()<4 ||  userDto.getPassword().length()>24){
            result.rejectValue("password", null, "password should consist of 4-24 characters");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "register-form";
        }

        userService.create(userDto);

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
    @PostMapping("/editProfile/{userId}")
    public String editProfile(@PathVariable Long userId){

        return "redirect:/Z/editProfile/{userId}";
    }

    @GetMapping("/editProfile/{userId}")
    public String editUser(@PathVariable Long userId, Model model){

        UserDto currentUser = userService.getUser(userId);
        model.addAttribute("currentUser", currentUser);

        return "edit-profile";
    }

    @PostMapping("/editProfile/save/{userId}")
    public String savedEditedProfile(@ModelAttribute("currentUser") @Valid UserDto userDto,
                                     BindingResult result,
                                     @PathVariable Long userId,
                                     Model model,
                                     HttpServletRequest request) throws ServletException {

        userDto.setId(userId);
        UserDto userBeforeUpdate = userService.getUser(userId);
        UserDto existingUser;

        // username & email db validation
        if(!Objects.equals(userBeforeUpdate.getEmail(), userDto.getEmail())){
            existingUser = userService.findUserByEmail(userDto.getEmail());
            if(existingUser!=null){
                result.rejectValue("email", null, "Email already in database");
            }
        }

        if(!Objects.equals(userBeforeUpdate.getUsername(), userDto.getUsername())){
            existingUser = userService.findUserByUsername(userDto.getUsername());
            if(existingUser!=null){
                result.rejectValue("username", null, "Username already in database");
            }
        }

        if(result.hasErrors()){
            model.addAttribute("currentUser", userDto);
            return "edit-profile";
        }

        userService.updateUser(userDto);
        request.logout();

        return "redirect:/Z/login?profileUpdated";
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

        List<UserDto> results = userService.findUsers(searchValue);
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

        return "redirect:/Z/news?page=0";
    }
}
