package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.DefaultWeekWorkHoursDto;

public interface DefaultWeekWorkHoursService {
    DefaultWeekWorkHoursDto setDefaultWeekWorkHours(Long employeeId, DefaultWeekWorkHoursDto dto);
    DefaultWeekWorkHoursDto readDefaultWeekWorkHours(Long employeeId);
    DefaultWeekWorkHoursDto updateDefaultWeekWorkHours(Long employeeId, DefaultWeekWorkHoursDto dto);
    void deleteDefaultWeekWorkHours(Long employeeId);
}
