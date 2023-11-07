package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.VisitDto;

import java.util.List;

public interface ClientVisitService {
    VisitDto createVisit(Long clientId, Long employeeId, VisitDto visitDto);
    VisitDto readVisitForClient(Long visitId);
    List<VisitDto> readVisitsForClient(Long clientId);
    void cancelVisit(Long visitId);
}
