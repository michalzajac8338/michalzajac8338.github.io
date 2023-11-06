package com.michal.booksylikeapp.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {

    private String name;
    private Double cost;
    private Integer durationInMin;
}
