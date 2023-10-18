package com.michal.socialnetworkingsite.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {


    private Long id;
    @Size(min=2, max=24, message = "first name should consist of 2-24 characters")
    private String firstName;
    @Size(min=2, max=24, message = "last name should consist of 2-24 characters")
    private String lastName;
    @Email(message = "not a valid email")
    private String email;
    @Size(min=4, max=24, message = "username should consist of 4-24 characters")
    private String username;
    private String password;
    private List<Long> following;
    private List<Long> followers;

}
