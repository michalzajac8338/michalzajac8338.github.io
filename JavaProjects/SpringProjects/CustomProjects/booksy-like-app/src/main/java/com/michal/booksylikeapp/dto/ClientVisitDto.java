package com.michal.booksylikeapp.dto;

import com.michal.booksylikeapp.constants.VisitStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
