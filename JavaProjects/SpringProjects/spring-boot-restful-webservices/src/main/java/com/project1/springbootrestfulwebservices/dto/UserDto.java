package com.project1.springbootrestfulwebservices.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        description = "UserDto Model Info"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    // without sensitive info
    private Long id;
    @Schema(description = "User first name")
    @NotEmpty(message = "this field should not be empty")
    private String firstName;
    @Schema(description = "User last name")
    @NotEmpty(message = "this field should not be empty")
    private String lastName;
    @Schema(description = "User's email")
    @NotEmpty(message = "this field should not be empty")
    @Email(message = "this field should be valid email address")
    private String email;

}
