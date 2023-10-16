package com.michal.socialnetworkingsite.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    @NotBlank(message = "cant add blank comment")
    @Size(min=1, max=511, message = "comment should consist of 1-511 characters")
    private String content;
    private String lastUpdated;
    private String username;
    private List<CommentLikeDto> likes;

}
