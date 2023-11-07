package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.ServiceDto;

import java.util.List;
import java.util.Set;

public interface ServiceService {
    ServiceDto addService(Long employeeId, ServiceDto serviceDto);
    Set<ServiceDto> readEmployeeServices(Long employeeId);
    ServiceDto updateService(Long employeeId, ServiceDto serviceDto);
    void deleteService(Long employeeId, ServiceDto serviceDto);
}
