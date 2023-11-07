package com.michal.booksylikeapp.mapper;

import com.michal.booksylikeapp.dto.ServiceDto;
import com.michal.booksylikeapp.entity.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class ServiceMapper {

    public static Service mapToService(ServiceDto serviceDto, Service service){

        if(service == null){
            service = new Service();
        }

        service.setName(serviceDto.getName());
        service.setCost(serviceDto.getCost());
        service.setDuration(Duration.of(serviceDto.getDurationInMin(), ChronoUnit.MINUTES));

        return service;
    }

    public static ServiceDto mapToServiceDto(Service service){

        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(service.getId());
        serviceDto.setName(service.getName());
        serviceDto.setCost(service.getCost());
        serviceDto.setDurationInMin((int) service.getDuration().getSeconds()/60);

        return serviceDto;
    }
}
