package com.michal.booksylikeapp.mapper;

import com.michal.booksylikeapp.dto.ServiceDto;
import com.michal.booksylikeapp.entity.Service;

public class ServiceMapper {

    public static Service mapToService(ServiceDto serviceDto, Service service){

        if(service == null){
            service = new Service();
        }

        service.setName(serviceDto.getName());
        service.setCost(serviceDto.getCost());
        service.setDuration(serviceDto.getDurationInMin());
        return service;
    }

    public static ServiceDto mapToServiceDto(Service service){

        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setName(service.getName());

        return serviceDto;
    }
}
