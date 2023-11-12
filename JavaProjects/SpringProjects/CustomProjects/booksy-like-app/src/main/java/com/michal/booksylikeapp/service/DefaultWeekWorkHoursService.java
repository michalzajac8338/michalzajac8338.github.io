package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.DefaultWeekWorkHoursDto;

public interface DefaultWeekWorkHoursService {
    DefaultWeekWorkHoursDto setDefaultWeekWorkHours(Long employeeId, DefaultWeekWorkHoursDto dto);

    DefaultWeekWorkHoursDto readDefaultWeekWorkHours(Long employeeId);
}
