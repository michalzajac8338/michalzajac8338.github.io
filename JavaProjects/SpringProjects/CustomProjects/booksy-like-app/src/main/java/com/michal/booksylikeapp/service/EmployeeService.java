package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.ClientVisitDto;
import com.michal.booksylikeapp.dto.EmployeeDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeService {

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto readEmployee(Long enterpriseId, Long employeeId);

    EmployeeDto updateEmployee(Long enterpriseId, Long employeeId, EmployeeDto employeeDto);

    void deleteEmployee(Long enterpriseId, Long employeeId);

    List<LocalDateTime> getAllPossibleVisitTime(Long employeeId, int visitDuration);
}
