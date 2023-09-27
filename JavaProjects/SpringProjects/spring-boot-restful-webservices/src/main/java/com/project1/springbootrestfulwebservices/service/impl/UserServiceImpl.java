package com.project1.springbootrestfulwebservices.service.impl;

import com.project1.springbootrestfulwebservices.dto.UserDto;
import com.project1.springbootrestfulwebservices.entity.User;
import com.project1.springbootrestfulwebservices.exception.EmailAlreadyExistsException;
import com.project1.springbootrestfulwebservices.exception.ResourceNotFoundException;
import com.project1.springbootrestfulwebservices.repository.UserRepository;
import com.project1.springbootrestfulwebservices.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Just one constructor => no need to use @Autowired; DI will be provided
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        //convert dto to user
//        User user = UserMapper.mapToUser(userDto);
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if(optionalUser.isPresent()){
            throw new EmailAlreadyExistsException("user with this email already exist in db");
        }

        User user = modelMapper.map(userDto, User.class);

        User savedUser = userRepository.save(user);

        //convert user JPA to DTO
//        UserDto savedUserDto = UserMapper.mapToUserDto(savedUser);
        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);

        return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long id) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

//        UserDto userDto = UserMapper.mapToUserDto(user);
        UserDto userDto = modelMapper.map(user, UserDto.class);

        return userDto;
    }

    @Override
    public List<UserDto> getAll() {

        List<User> users = userRepository.findAll();

//        List<UserDto> usersDto = users.stream().map(UserMapper::mapToUserDto).toList();
        List<UserDto> usersDto = users.stream().map(u->modelMapper.map(u,UserDto.class)).toList();

        return usersDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {

        User retrievedUser = userRepository.findById(userDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User","id", userDto.getId())
        );
        retrievedUser.setFirstName(userDto.getFirstName());
        retrievedUser.setLastName(userDto.getLastName());
        retrievedUser.setEmail(userDto.getEmail());

        User savedUser = userRepository.save(retrievedUser);

//        UserDto userDto1 = UserMapper.mapToUserDto(savedUser);
        UserDto userDto1 = modelMapper.map(savedUser,UserDto.class);

        return userDto1;
    }

    @Override
    public void deleteUser(Long userId) {
        User retrievedUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User","id", userId)
        );
        userRepository.deleteById(userId) ;
    }
}
