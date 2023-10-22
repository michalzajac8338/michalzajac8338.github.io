package com.michal.booksylikeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

}
