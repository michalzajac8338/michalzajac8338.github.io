package com.michal.socialnetworkingsite.security;

import com.michal.socialnetworkingsite.entity.User;
import com.michal.socialnetworkingsite.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(authority));
    }
}
