package com.michal.springboot.dto;

import com.michal.springboot.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    private  Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email(message = "not a valid email")
    @NotEmpty
    private String email;
    @NotEmpty(message = "password should not be empty")
    private String password;

    public void setFirstAndLastName(String name) {

        String[] splitName = name.split(" ");

        this.firstName = splitName[0];
        this.lastName = splitName[1];
    }

    public String getFirstAndLastName(){
        return this.getFirstName()+this.getLastName();
    }
}
