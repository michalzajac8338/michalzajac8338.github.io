package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.ClientVisitDto;

public interface ClientVisitService {
    ClientVisitDto createVisit(Long clientId, Long employeeId, ClientVisitDto clientVisitDto);
}
