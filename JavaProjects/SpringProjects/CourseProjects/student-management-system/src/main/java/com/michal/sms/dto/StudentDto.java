package com.michal.sms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private Long id;
    @NotEmpty(message = "first name should not be empty")
    private String firstName;
    @NotEmpty(message = "last name should not be empty")
    private String lastName;
    @NotEmpty(message = "email should not be empty")
    @Email
    private String email;



}
