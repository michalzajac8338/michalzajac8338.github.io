package com.project1.springbootrestfulwebservices.mapper;

import com.project1.springbootrestfulwebservices.dto.UserDto;
import com.project1.springbootrestfulwebservices.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoUserMapper {

    AutoUserMapper mapper = Mappers.getMapper(AutoUserMapper.class);

    UserDto mapToUserDto(User user);
    User mapToUser(UserDto userDto);
}
