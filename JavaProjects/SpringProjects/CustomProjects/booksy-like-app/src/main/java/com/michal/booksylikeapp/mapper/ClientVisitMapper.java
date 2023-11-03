package com.michal.booksylikeapp.mapper;

import com.michal.booksylikeapp.constants.VisitStatus;
import com.michal.booksylikeapp.dto.ClientVisitDto;
import com.michal.booksylikeapp.entity.Client;
import com.michal.booksylikeapp.entity.Visit;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.MINUTES;

public class ClientVisitMapper {

    public static Visit mapToVisit(ClientVisitDto clientVisitDto, Visit visit){

        if(visit == null) {
            visit = new Visit();
        }

        visit.setCost(clientVisitDto.getCost());
        visit.setStartTime(LocalDateTime.parse(clientVisitDto.getStartTime()));
        visit.setDuration(Duration.ofMinutes(clientVisitDto.getDurationInMin()));
        visit.setDescription(clientVisitDto.getDescription());
        visit.setStatus(VisitStatus.valueOf(String.valueOf(clientVisitDto.getStatus())));
        visit.setCost(clientVisitDto.getCost());
        visit.setType(clientVisitDto.getType());

        return visit;
    }

    public static ClientVisitDto mapToClientVisitDto(Visit visit){

        ClientVisitDto clientVisitDto = new ClientVisitDto();

        clientVisitDto.setId(visit.getId());
        clientVisitDto.setCost(visit.getCost());
        clientVisitDto.setStartTime(visit.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        clientVisitDto.setDurationInMin((int) visit.getDuration().getSeconds()/60);
        clientVisitDto.setDescription(visit.getDescription());
        clientVisitDto.setStatus(visit.getStatus().toString());
        clientVisitDto.setCost(visit.getCost());
        clientVisitDto.setType(visit.getType());

        return clientVisitDto;

    }

}
