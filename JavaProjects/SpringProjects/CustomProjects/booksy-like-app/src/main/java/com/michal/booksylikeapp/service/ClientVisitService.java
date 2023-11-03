package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.ClientVisitDto;

import java.util.List;

public interface ClientVisitService {
    ClientVisitDto createVisit(Long clientId, Long employeeId, ClientVisitDto clientVisitDto);

    void cancelVisit(Long visitId);

    List<ClientVisitDto> readVisitsForClient(Long clientId);
}
