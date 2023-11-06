package com.michal.booksylikeapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientVisitDto {

    private Long id;
    private String startTime;
    private String status;
    private String description;

    private String name;
    private Double cost;
    private Integer durationInMin;

}
