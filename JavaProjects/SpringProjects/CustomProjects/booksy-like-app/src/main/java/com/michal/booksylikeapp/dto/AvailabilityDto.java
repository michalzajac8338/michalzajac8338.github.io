package com.michal.booksylikeapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDto {

    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime workStartTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime workEndTime;

}
