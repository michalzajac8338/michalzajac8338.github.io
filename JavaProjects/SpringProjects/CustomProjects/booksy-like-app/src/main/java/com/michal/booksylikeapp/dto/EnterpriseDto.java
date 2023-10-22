package com.michal.booksylikeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseDto {

    private Long id;
    private String name;
    private String description;
    private String email;
    private String username;
    private String password;

}
