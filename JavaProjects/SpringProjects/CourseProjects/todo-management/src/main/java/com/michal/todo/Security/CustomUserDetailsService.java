package com.michal.todo.Security;

import com.michal.todo.entity.User;
import com.michal.todo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
                ()->new UsernameNotFoundException("User doesn't exist by username or email")
        );

        // Set of Roles -> Set od GrantedAuthority
        Set<GrantedAuthority> authorities = user.getRoles().stream().map(
                role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.getPassword(),
                authorities);
    }
}
