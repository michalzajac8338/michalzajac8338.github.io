package com.michal.springboot.service.impl;

import com.michal.springboot.dto.UserDto;
import com.michal.springboot.entity.Role;
import com.michal.springboot.entity.User;
import com.michal.springboot.repository.RoleRepository;
import com.michal.springboot.repository.UserRepository;
import com.michal.springboot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");

        if(role==null){
            role=checkRoleExists();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {

        User user = userRepository.findByEmail(email);

        return user;
    }

    @Override
    public Set<UserDto> findAllUsers() {
        Set<User> users = new HashSet<>(userRepository.findAll());

        return users.stream().map(this::mapToUserDto).collect(Collectors.toSet());
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    private Role checkRoleExists(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

}
