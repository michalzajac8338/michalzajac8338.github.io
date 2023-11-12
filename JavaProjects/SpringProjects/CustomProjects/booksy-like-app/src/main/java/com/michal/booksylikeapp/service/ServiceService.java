package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.ServiceDto;

import java.util.Set;

public interface ServiceService {

    // CRUD
    ServiceDto addService(Long employeeId, ServiceDto serviceDto);
    Set<ServiceDto> readEmployeeServices(Long employeeId);
    Set<ServiceDto> readEnterpriseServices(Long enterpriseId);
    ServiceDto updateService(Long employeeId, ServiceDto serviceDto);
    void deleteService(Long employeeId, ServiceDto serviceDto);
}
