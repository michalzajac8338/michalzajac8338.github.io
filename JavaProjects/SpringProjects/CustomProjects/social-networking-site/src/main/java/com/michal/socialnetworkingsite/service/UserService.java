package com.michal.socialnetworkingsite.service;

import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.entity.User;

import java.util.List;

public interface UserService {
    UserDto save(UserDto userDto);
    List<UserDto> getAllUsers();
    void followOrUnfollowUser(String currentUserName, String otherUserName);
}
