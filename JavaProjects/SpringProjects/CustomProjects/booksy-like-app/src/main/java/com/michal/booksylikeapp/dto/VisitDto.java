package com.michal.booksylikeapp.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitDto {

    private Long id;
    private String startTime;
    private String status;
    private String clientMessage;

    private Long serviceId;
}
