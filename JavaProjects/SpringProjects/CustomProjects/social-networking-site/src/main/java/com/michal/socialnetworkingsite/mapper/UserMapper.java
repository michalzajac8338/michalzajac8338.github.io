package com.michal.socialnetworkingsite.mapper;

import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.entity.User;

public class UserMapper {

    public static User mapToUser(UserDto userDto, User user){

        if(user==null)
            {user = new User();}

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());

        return user;
    }

    public static UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
        userDto.setUsername(user.getUsername());
        userDto.setFollowing(user.getFollowing().stream().map(User::getId).toList());
        userDto.setFollowers(user.getFollowers().stream().map(User::getId).toList());

        return userDto;
    }


}
