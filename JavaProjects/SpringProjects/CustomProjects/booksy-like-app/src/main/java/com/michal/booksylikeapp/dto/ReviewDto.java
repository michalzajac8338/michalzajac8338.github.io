package com.michal.booksylikeapp.dto;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long reviewAndVisitId;
    private Integer rating;
    private String content;

}
