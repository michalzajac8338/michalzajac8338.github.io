package com.michal.springboot.service;

import com.michal.springboot.dto.UserDto;
import com.michal.springboot.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    Set<UserDto> findAllUsers();

}
