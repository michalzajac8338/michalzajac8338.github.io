package com.michal.booksylikeapp.dto;

import lombok.*;

@Getter
@Setter
@Builder
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
