package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.dto.ServiceDto;
import com.michal.booksylikeapp.entity.Employee;
import com.michal.booksylikeapp.entity.Service;
import com.michal.booksylikeapp.mapper.ServiceMapper;
import com.michal.booksylikeapp.repository.EmployeeRepository;
import com.michal.booksylikeapp.repository.ServiceRepository;

import com.michal.booksylikeapp.service.ServiceService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.stereotype.Service;

import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private EmployeeRepository employeeRepository;
    private ServiceRepository serviceRepository;

    @Override
    @Transactional
    public ServiceDto addService(Long employeeId, ServiceDto serviceDto) {

        // check if service already exists (e.g. is served by another employee)
        Service existingService = serviceRepository.findByNameAndCostAndDuration(serviceDto.getName(),
                serviceDto.getCost(), (int) (serviceDto.getDurationInMin()/(6*1e10)));

        Service service = ServiceMapper.mapToService(serviceDto, existingService);

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);

        employee.getServices().add(service);
        service.getEmployees().add(employee);

        employeeRepository.save(employee);
        serviceRepository.save(service);

        return ServiceMapper.mapToServiceDto(service);
    }

    @Override
    public List<ServiceDto> readEmployeeServices(Long employeeId) {
        return null;
    }

    @Override
    public ServiceDto updateService(Long employeeId, ServiceDto serviceDto) {
        return null;
    }

    @Override
    public void deleteService(Long employeeId, ServiceDto serviceDto) {

    }
}
