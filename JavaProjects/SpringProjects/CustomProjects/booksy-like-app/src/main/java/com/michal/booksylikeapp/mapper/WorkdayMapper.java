package com.michal.booksylikeapp.mapper;

import com.michal.booksylikeapp.dto.WorkdayDto;
import com.michal.booksylikeapp.entity.Workday;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WorkdayMapper {

    public static Workday mapToWorkday(WorkdayDto workdayDto, Workday workday){

        // when creating
        if(workday == null) {
            workday = new Workday();
        }

        workday.setDate(LocalDate.parse(workdayDto.getDate()));
        workday.setWorkStartTime(LocalDateTime.parse(workdayDto.getWorkStartTime()));
        workday.setWorkEndTime(LocalDateTime.parse(workdayDto.getWorkEndTime()));

        return workday;
    }

    public static WorkdayDto mapToWorkdayDto(Workday workday){

        WorkdayDto workdayDto = new WorkdayDto();

        workdayDto.setId(workday.getId());
        workdayDto.setDate(workday.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        workdayDto.setWorkStartTime(workday.getWorkStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        workdayDto.setWorkEndTime(workday.getWorkEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        return workdayDto;
    }


}
