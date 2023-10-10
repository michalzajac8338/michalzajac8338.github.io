package com.michal.socialnetworkingsite.controller;

import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("Z")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping
    public String home(){
        return "home-page";
    }

    @GetMapping("/register")
    public String registerUser(Model model){

        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);

        return "register-form";
    }

    @GetMapping("/login")
    public String loginUser(Model model){

        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);

        return "user-login";
    }

    @PostMapping("/register/save")
    public String registerUser(@ModelAttribute("user") UserDto userDto){

        userService.save(userDto);

        return "redirect:/Z/login?registered_successfully";
    }

    @PostMapping("/follow/{username}")
    public String follow(@PathVariable String username){

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        userService.followOrUnfollowUser(currentUserName, username);
        return "redirect:/Z/news";

    }


}
