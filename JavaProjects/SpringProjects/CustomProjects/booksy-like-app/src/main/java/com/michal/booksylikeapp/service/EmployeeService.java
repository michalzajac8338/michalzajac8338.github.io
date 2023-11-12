package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.EmployeeDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface EmployeeService {

    // CRUD
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto readEmployee(Long enterpriseId, Long employeeId);
    EmployeeDto updateEmployee(Long enterpriseId, Long employeeId, EmployeeDto employeeDto);
    void deleteEmployee(Long enterpriseId, Long employeeId);

    // Additionally
    List<LocalDateTime> getAllPossibleVisitTimeForEmployee(Long employeeId, Long serviceId);
}
