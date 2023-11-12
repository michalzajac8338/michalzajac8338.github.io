package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.VisitDto;

import java.util.List;

public interface ClientVisitService {

    // CRD
    VisitDto createVisit(Long clientId, Long employeeId, VisitDto visitDto);
    VisitDto readVisitForClient(Long visitId);
    List<VisitDto> readAllVisitsForClient(Long clientId);
    void cancelVisit(Long visitId);
}
