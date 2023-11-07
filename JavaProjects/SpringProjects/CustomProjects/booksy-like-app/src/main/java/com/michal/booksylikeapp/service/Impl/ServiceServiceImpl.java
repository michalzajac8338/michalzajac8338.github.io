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

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private EmployeeRepository employeeRepository;
    private ServiceRepository serviceRepository;

    @Override
    @Transactional
    public ServiceDto addService(Long employeeId, ServiceDto serviceDto) {

        // check if service already exists (e.g. is served by another employee)
        Service existingService = serviceRepository.findByNameAndCostAndDuration(
                serviceDto.getName(), serviceDto.getCost(), Duration.of(serviceDto.getDurationInMin(),MINUTES)).orElse(null);
        Service service = ServiceMapper.mapToService(serviceDto, existingService);

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);

        employee.getServices().add(service);
        service.getEmployees().add(employee);

        employeeRepository.save(employee);
        serviceRepository.save(service);

        return ServiceMapper.mapToServiceDto(service);
    }

    @Override
    public Set<ServiceDto> readEmployeeServices(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Set<Service> services = employee.getServices();

        return services.stream().map(ServiceMapper::mapToServiceDto).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public ServiceDto updateService(Long employeeId, ServiceDto serviceDto) {

        Service service = serviceRepository.findById(serviceDto.getId()).orElseThrow(RuntimeException::new);

        if(service.getEmployees().size() == 1){
            service = ServiceMapper.mapToService(serviceDto, service);
            Service updatedService = serviceRepository.save(service);
            return ServiceMapper.mapToServiceDto(updatedService);
        } else {
            // if someone else is using this service, we have to create new one (or use other existing)
            return addService(employeeId, serviceDto);
        }
    }

    @Override
    @Transactional
    public void deleteService(Long employeeId, ServiceDto serviceDto) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Service service = serviceRepository.findById(serviceDto.getId()).orElseThrow(RuntimeException::new);

        employee.getServices().remove(service);
        service.getEmployees().remove(employee);

        if(service.getEmployees() == null){
            serviceRepository.delete(service);
        }
    }
}
