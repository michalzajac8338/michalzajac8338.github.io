package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.AvailabilityDto;

import java.util.List;

public interface AvailabilityService {
    void createAvailability(Long employeeId, AvailabilityDto availabilityDto);

    List<AvailabilityDto> readAvailabilityList(Long employeeId);

    void updateAvailability(Long employeeId, AvailabilityDto availabilityDto);

    void deleteAvailability(Long employeeId, AvailabilityDto availabilityDto);
}
