package com.michal.socialnetworkingsite.dto;


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
    private String content;
    private String creator;
    private String lastUpdated;
    private List<CommentDto> comments;
    private List<PostLikeDto> likes;

}
