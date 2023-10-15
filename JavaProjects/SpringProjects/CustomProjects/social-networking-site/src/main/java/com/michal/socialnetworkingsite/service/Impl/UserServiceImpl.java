package com.michal.socialnetworkingsite.service.Impl;

import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.entity.User;
import com.michal.socialnetworkingsite.mapper.UserMapper;
import com.michal.socialnetworkingsite.repository.UserRepository;
import com.michal.socialnetworkingsite.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public void save(UserDto userDto) {

        User user = UserMapper.mapToUser(userDto, null);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(UserDto userDto) {

        User user = userRepository.findById(userDto.getId()).get();
        User user1 = UserMapper.mapToUser(userDto, user);
        if(userDto.getPassword()!=null) {
            user1.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userRepository.save(user1);

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
        User otherUser = userRepository.findById(userId).get();

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
    public List<UserDto> getUsers(String name) {

        List<User> users = userRepository.findByUsernameContainsOrFirstNameContainsOrLastNameContains(name, name, name);

        return users.stream().map(UserMapper::mapToUserDto).toList();
    }

    @Override
    public UserDto getCurrentUser() {

        User currentUser = getLoggedInUser();
        return UserMapper.mapToUserDto(currentUser);
    }
    @Override
    @Transactional
    public void deleteCurrentUser() {

        User currentUser = getLoggedInUser();
        currentUser.setFollowing(null);

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

        User user = userRepository.findById(userId).get();
        return UserMapper.mapToUserDto(user);
    }

    private User getLoggedInUser(){
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
