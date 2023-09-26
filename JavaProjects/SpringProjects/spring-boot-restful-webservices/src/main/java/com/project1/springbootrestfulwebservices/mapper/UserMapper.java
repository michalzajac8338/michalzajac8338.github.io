package com.project1.springbootrestfulwebservices.mapper;

import com.project1.springbootrestfulwebservices.dto.UserDto;
import com.project1.springbootrestfulwebservices.entity.User;

public class UserMapper {

    //JPA Entity -> DTO
    public static UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );

        return userDto;
    }

    //DTO->JPA
    public static User mapToUser(UserDto userDto){
        User user = new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail()
        );

        return user;
    }

}
