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
public class PostDto {

    private Long id;
    @NotBlank(message = "cant add blank post")
    @Size(min=1, max=511, message = "post should consist of 1-511 characters")
    private String content;
    private String creator;
    private String lastUpdated;
    private List<CommentDto> comments;
    private List<PostLikeDto> likes;

}
