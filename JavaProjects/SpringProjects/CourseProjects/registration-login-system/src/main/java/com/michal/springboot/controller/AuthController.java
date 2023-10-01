package com.michal.springboot.controller;

import com.michal.springboot.dto.UserDto;
import com.michal.springboot.entity.User;
import com.michal.springboot.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    //handler for homepage
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    //handler for login request

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    //handler for registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // model obj to store form data
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    //handler for registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){

        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null){
            result.rejectValue("email", null, "There is already an Account existing with this email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    //handler for user list
    @GetMapping("users")
    public String users(Model model){
        Set<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }


}
