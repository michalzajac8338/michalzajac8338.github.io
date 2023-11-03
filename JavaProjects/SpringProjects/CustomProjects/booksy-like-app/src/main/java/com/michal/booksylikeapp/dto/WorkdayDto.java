package com.michal.booksylikeapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkdayDto {

    private Long id;
    private Long employeeId;
    private String date;
    private String workStartTime;
    private String workEndTime;

}
