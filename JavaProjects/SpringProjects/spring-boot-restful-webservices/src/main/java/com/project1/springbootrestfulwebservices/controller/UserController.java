package com.project1.springbootrestfulwebservices.controller;

import com.project1.springbootrestfulwebservices.dto.UserDto;
import com.project1.springbootrestfulwebservices.entity.User;
import com.project1.springbootrestfulwebservices.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private UserService userService; //injected by constructor

    // build create user REST API
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user){
        UserDto savedUserDto = userService.createUser(user);
        System.out.println(savedUserDto);
        return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/users/1
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id){
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }
    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(){
        List<UserDto> userList = userService.getAll();
        return ResponseEntity.ok(userList);
    }

    //http://localhost:8080/api/users/1
    @PutMapping("{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto userDto){
        userDto.setId(id);
        UserDto u = userService.updateUser(userDto);
        return ResponseEntity.ok(u);
    }

    //http://localhost:8080/api/users/1
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User " + id + " deleted");
    }
}
