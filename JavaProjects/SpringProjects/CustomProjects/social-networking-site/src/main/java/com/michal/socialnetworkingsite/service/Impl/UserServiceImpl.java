package com.michal.socialnetworkingsite.service.Impl;

import com.michal.socialnetworkingsite.dto.UserDto;
import com.michal.socialnetworkingsite.entity.User;
import com.michal.socialnetworkingsite.mapper.UserMapper;
import com.michal.socialnetworkingsite.repository.UserRepository;
import com.michal.socialnetworkingsite.service.UserService;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
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
    public UserDto save(UserDto userDto) {

        User user = UserMapper.mapToUser(userDto, null);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public void updateUser(UserDto userDto) {

        User user = userRepository.findById(userDto.getId()).get();
        User user1 = UserMapper.mapToUser(userDto, user);
        if(userDto.getPassword()!=null) {
            user1.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userRepository.save(user1);

    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream().map(UserMapper::mapToUserDto).toList();
    }

    @Override
    @Transactional
    public void followOrUnfollowUser(String currentUserName, String otherUserName) {

        User currentUser = userRepository.findByUsername(currentUserName);
        User otherUser = userRepository.findByUsername(otherUserName);

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
    public UserDto getCurrentUser(String currentUserName) {

        User user = userRepository.findByUsername(currentUserName);
        UserDto currentUserDto = UserMapper.mapToUserDto(user);

        return currentUserDto;
    }

    @Override
    @Transactional
    public void deleteProfile(String currentUserName) {

        User user = userRepository.findByUsername(currentUserName);

        //clearing follower table manually

        user.setFollowing(null);

        List<User> followers = userRepository.findByFollowing(user);

        List<User> followersUpdate = followers.stream()
                .map(follower -> {
                    List<User> f = follower.getFollowing();
                    f.remove(user);
                    follower.setFollowing(f);
                    return follower; }
                ).toList();

        userRepository.saveAll(followersUpdate);
        userRepository.delete(user);

    }

    @Override
    public UserDto getUser(Long userId) {

        User user = userRepository.findById(userId).get();

        return UserMapper.mapToUserDto(user);
    }
}
