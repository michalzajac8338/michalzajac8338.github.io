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
    private Integer durationInMin;
    private String status;
    private Double cost;
    private String type;
    private String description;

}
