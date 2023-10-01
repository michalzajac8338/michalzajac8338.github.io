package com.michal.springboot.config;

import com.michal.springboot.security.CustomUserDtoService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurity {

    //configure security filter chain

    private CustomUserDtoService customUserDtoService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("index").permitAll()
                        .requestMatchers("/users").hasRole("ADMIN"))

                        //.anyRequest().authenticated())
//                .anyRequest()
//                .authenticated()
//                .and()
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/users")
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll());

        return http.build();

    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorize -> authorize
//                        .anyRequest().authenticated()
//                )
//                .formLogin(withDefaults())
//                .httpBasic(withDefaults());
//        return http.build();
//    }

//
//        http.csrf((csrf)-> csrf.disable())
//                .authorizeHttpRequests((authorize) -> {
////                    authorize.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN");
////                    authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
////                    authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
//////                    authorize.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER");
////                    authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll();
////                    authorize.requestMatchers(HttpMethod.PATCH, "/api/**").hasAnyRole("ADMIN","USER");
//                    authorize.anyRequest().authenticated();
//                })
//                .httpBasic(Customizer.withDefaults());
//
}
