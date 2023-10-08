package com.michal.socialnetworkingsite.service;

import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.entity.User;

public interface UserService {
    UserDto save(UserDto userDto);
}
