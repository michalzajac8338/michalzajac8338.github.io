package com.michal.socialnetworkingsite.service.Impl;

import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.entity.User;
import com.michal.socialnetworkingsite.exception.ResourceNotFoundException;
import com.michal.socialnetworkingsite.mapper.UserMapper;
import com.michal.socialnetworkingsite.repository.UserRepository;
import com.michal.socialnetworkingsite.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public void create(UserDto userDto) {

        User user = UserMapper.mapToUser(userDto, null);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(UserDto userDto) {

        User user = userRepository.findById(userDto.getId()).orElseThrow(ResourceNotFoundException::new);
        User updatedUser = UserMapper.mapToUser(userDto, user);
        if(userDto.getPassword()!=null && !userDto.getPassword().isBlank()) {
            updatedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userRepository.save(updatedUser);

    }

    @Override
    public List<UserDto> getRandomUsers() {

        List<User> users = userRepository.getThreeRandomUsers();
        return users.stream().map(UserMapper::mapToUserDto).toList();
    }

    @Override
    @Transactional
    public void followOrUnfollowUser(Long userId) {

        User currentUser = getLoggedInUser();
        User otherUser = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);

        List<User> followers = otherUser.getFollowers();
        List<User> following = currentUser.getFollowing();

        if(followers.contains(currentUser)) {
            followers.remove(currentUser);
            following.remove(otherUser);
        } else {
            followers.add(currentUser);
            following.add(otherUser);
        }

        userRepository.save(otherUser);
        userRepository.save(currentUser);

    }

    @Override
    public List<UserDto> findUsers(String name) {

        List<User> users = userRepository.findByUsernameContainsOrFirstNameContainsOrLastNameContains(name, name, name);
        return users.stream().map(UserMapper::mapToUserDto).toList();
    }


    @Override
    public UserDto getCurrentUser() {
        return UserMapper.mapToUserDto(getLoggedInUser());
    }

    @Override
    @Transactional
    public void deleteCurrentUser() {

        User currentUser = getLoggedInUser();
        currentUser.setFollowing(null);

        // manually deleting following & followers lists
        List<User> followers = userRepository.findByFollowing(currentUser);
        List<User> followersUpdate = followers.stream()
                .map(follower -> {
                    List<User> f = follower.getFollowing();
                    f.remove(currentUser);
                    follower.setFollowing(f);
                    return follower; }
                ).toList();

        userRepository.saveAll(followersUpdate);
        userRepository.delete(currentUser);
    }

    @Override
    public UserDto getUser(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto findUserByEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        return user.map(UserMapper::mapToUserDto).orElse(null);
    }

    @Override
    public UserDto findUserByUsername(String username) {

        Optional<User> user = userRepository.findByUsername(username);
        return user.map(UserMapper::mapToUserDto).orElse(null);
    }
    private User getLoggedInUser(){
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
    }
}
