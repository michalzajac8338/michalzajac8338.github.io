package com.michal.booksylikeapp.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DefaultWeekWorkHoursDto {

    private Map<String, Map<String, String>> dayOfWeekWorkHours;
}
