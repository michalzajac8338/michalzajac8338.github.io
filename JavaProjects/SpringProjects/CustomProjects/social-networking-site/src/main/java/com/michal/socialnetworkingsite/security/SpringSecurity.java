package com.michal.socialnetworkingsite.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@AllArgsConstructor
public class SpringSecurity {

    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a->{

                    a.requestMatchers("/Z", "/Z/login/**", "/Z/register/**").permitAll();
                    a.requestMatchers("/images/**").permitAll();
                    a.anyRequest().authenticated();
                })
                .formLogin(form ->
                        form.loginPage("/Z/login").loginProcessingUrl("/Z/login").permitAll()
                                .defaultSuccessUrl("/Z/news?page=0")
                )
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/Z/logout")));

        return http.build();

    }
}
