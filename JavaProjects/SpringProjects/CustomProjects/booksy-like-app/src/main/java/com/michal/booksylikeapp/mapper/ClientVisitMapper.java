package com.michal.booksylikeapp.mapper;

import com.michal.booksylikeapp.constants.VisitStatus;
import com.michal.booksylikeapp.dto.ClientVisitDto;
import com.michal.booksylikeapp.entity.Client;
import com.michal.booksylikeapp.entity.Service;
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

        visit.setStartTime(LocalDateTime.parse(clientVisitDto.getStartTime()));
        visit.setDescription(clientVisitDto.getDescription());
        visit.setStatus(VisitStatus.valueOf(String.valueOf(clientVisitDto.getStatus())));

        return visit;
    }

    public static ClientVisitDto mapToClientVisitDto(Visit visit, Service service){

        ClientVisitDto clientVisitDto = new ClientVisitDto();

        clientVisitDto.setId(visit.getId());
        clientVisitDto.setStartTime(visit.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        clientVisitDto.setDescription(visit.getDescription());
        clientVisitDto.setStatus(visit.getStatus().toString());

        clientVisitDto.setName(service.getName());
        clientVisitDto.setCost(service.getCost());
        clientVisitDto.setDurationInMin((int) service.getDuration().getSeconds()/60);

        return clientVisitDto;

    }

}
