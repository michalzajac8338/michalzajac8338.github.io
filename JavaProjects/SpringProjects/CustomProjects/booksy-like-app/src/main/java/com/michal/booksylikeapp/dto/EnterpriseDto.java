package com.michal.booksylikeapp.dto;

import lombok.*;

@Getter
@Setter
@Builder
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
