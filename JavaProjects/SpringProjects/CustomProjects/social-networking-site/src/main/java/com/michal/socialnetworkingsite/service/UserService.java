package com.michal.socialnetworkingsite.service;

import com.michal.socialnetworkingsite.dto.UserDto;

import java.util.List;

public interface UserService {
    void save(UserDto userDto);
    UserDto getCurrentUser();
    UserDto getUser(Long userId);
    List<UserDto> getRandomUsers();
    void updateUser(UserDto userDto);

    void deleteCurrentUser();
    void followOrUnfollowUser(Long userId);

    List<UserDto> getUsers(String name);
}
