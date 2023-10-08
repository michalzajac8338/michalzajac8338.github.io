package com.michal.socialnetworkingsite.controller;

import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String registerUser(@ModelAttribute("user") UserDto userDto,
                               Model model){

        model.addAttribute("user", userDto);
        userService.save(userDto);

        return "redirect:/Z/news";
    }


}
