package com.michal.booksylikeapp.mapper;

import com.michal.booksylikeapp.dto.WorkdayDto;
import com.michal.booksylikeapp.entity.Workday;

public class WorkdayMapper {

    public static Workday mapToWorkday(WorkdayDto workdayDto, Workday workday){

        // when creating
        if(workday == null) {
            workday = new Workday();
        }

        workday.setDate(workdayDto.getDate());
        workday.setWorkStartTime(workdayDto.getWorkStartTime());
        workday.setWorkEndTime(workdayDto.getWorkEndTime());

        return workday;
    }

    public static WorkdayDto mapToWorkdayDto(Workday workday){

        WorkdayDto workdayDto = new WorkdayDto();

        workdayDto.setId(workday.getId());
        workdayDto.setDate(workday.getDate());
        workdayDto.setWorkStartTime(workday.getWorkStartTime());
        workdayDto.setWorkEndTime(workday.getWorkEndTime());

        return workdayDto;
    }


}
