package com.michal.booksylikeapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.michal.booksylikeapp.entity.VisitStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientVisitDto {

    private LocalDateTime startTime;
    private Integer duration;
    private VisitStatus status;
    private Double cost;
    private String type;
    private String description;

}
