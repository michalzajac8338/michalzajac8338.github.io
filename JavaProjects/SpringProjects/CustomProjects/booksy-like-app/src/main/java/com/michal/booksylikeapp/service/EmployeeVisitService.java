package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.VisitDto;

import java.util.List;

public interface EmployeeVisitService {
    List<VisitDto> readEmployeeVisits(Long employeeId);
    VisitDto changeVisitStatus(Long employeeId, VisitDto visitDto);
}
