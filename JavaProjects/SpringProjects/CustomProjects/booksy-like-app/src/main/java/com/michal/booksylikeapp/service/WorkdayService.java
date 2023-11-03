package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.WorkdayDto;

import java.util.List;

public interface WorkdayService {
    WorkdayDto createWorkday(Long employeeId, WorkdayDto workdayDto);

    List<WorkdayDto> readWorkdayList(Long employeeId);

    WorkdayDto updateWorkday(Long employeeId, WorkdayDto workdayDto);

    void deleteWorkday(Long employeeId, WorkdayDto workdayDto);
}
