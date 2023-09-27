package com.project1.springbootrestfulwebservices.service;

import com.project1.springbootrestfulwebservices.dto.UserDto;
import com.project1.springbootrestfulwebservices.entity.User;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user);
    UserDto getUserById(Long id);
    List<UserDto> getAll();
    UserDto updateUser(UserDto user);
    void deleteUser(Long userId);
}
