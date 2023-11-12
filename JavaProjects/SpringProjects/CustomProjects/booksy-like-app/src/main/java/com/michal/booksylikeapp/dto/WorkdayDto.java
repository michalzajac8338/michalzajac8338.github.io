package com.michal.booksylikeapp.dto;

import lombok.*;

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
