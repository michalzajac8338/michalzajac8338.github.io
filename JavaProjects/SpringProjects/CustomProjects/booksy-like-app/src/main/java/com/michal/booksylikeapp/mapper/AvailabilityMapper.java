package com.michal.booksylikeapp.mapper;

import com.michal.booksylikeapp.dto.AvailabilityDto;
import com.michal.booksylikeapp.dto.ClientDto;
import com.michal.booksylikeapp.entity.Availability;
import com.michal.booksylikeapp.entity.Client;

public class AvailabilityMapper {

    public static Availability mapToAvailability(AvailabilityDto availabilityDto, Availability availability){

        // when creating
        if(availability == null) {
            availability = new Availability();
        }

        availability.setDate(availabilityDto.getDate());
        availability.setWorkStartTime(availabilityDto.getWorkStartTime());
        availability.setWorkEndTime(availabilityDto.getWorkEndTime());

        return availability;
    }

    public static AvailabilityDto mapToAvailabilityDto(Availability availability){

        AvailabilityDto availabilityDto = new AvailabilityDto();

        availabilityDto.setId(availability.getId());
        availabilityDto.setDate(availability.getDate());
        availabilityDto.setWorkStartTime(availability.getWorkStartTime());
        availabilityDto.setWorkEndTime(availability.getWorkEndTime());

        return availabilityDto;
    }


}
