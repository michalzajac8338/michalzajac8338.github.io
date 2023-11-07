package com.michal.booksylikeapp.mapper;

import com.michal.booksylikeapp.constants.VisitStatus;
import com.michal.booksylikeapp.dto.VisitDto;
import com.michal.booksylikeapp.entity.Visit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VisitMapper {

    public static Visit mapToVisit(VisitDto visitDto, Visit visit){

        if(visit == null) {
            visit = new Visit();
        }

        visit.setStartTime(LocalDateTime.parse(visitDto.getStartTime()));
        visit.setClientMessage(visitDto.getClientMessage());
        visit.setStatus(VisitStatus.valueOf(String.valueOf(visitDto.getStatus())));

        return visit;
    }

    public static VisitDto mapToVisitDto(Visit visit){

        VisitDto visitDto = new VisitDto();

        visitDto.setId(visit.getId());
        visitDto.setStartTime(visit.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        visitDto.setClientMessage(visit.getClientMessage());
        visitDto.setStatus(visit.getStatus().toString());

        visitDto.setServiceId(visit.getService().getId());

        return visitDto;

    }
}
