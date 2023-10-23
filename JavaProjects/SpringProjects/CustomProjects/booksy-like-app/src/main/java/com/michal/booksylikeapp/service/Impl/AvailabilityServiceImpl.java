package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.dto.AvailabilityDto;
import com.michal.booksylikeapp.entity.Availability;
import com.michal.booksylikeapp.entity.Employee;
import com.michal.booksylikeapp.mapper.AvailabilityMapper;
import com.michal.booksylikeapp.repository.AvailabilityRepository;
import com.michal.booksylikeapp.repository.EmployeeRepository;
import com.michal.booksylikeapp.service.AvailabilityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private EmployeeRepository employeeRepository;
    private AvailabilityRepository availabilityRepository;

    @Override
    public void createAvailability(Long employeeId, AvailabilityDto availabilityDto) {

        Availability availability = AvailabilityMapper.mapToAvailability(availabilityDto, null);

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        employee.getAvailabilityList().add(availability);
        employeeRepository.save(employee);

    }

    @Override
    public List<AvailabilityDto> readAvailabilityList(Long employeeId) {

        List<Availability> availabilityList = employeeRepository.findById(employeeId)
                .orElseThrow(RuntimeException::new).getAvailabilityList();

        List<AvailabilityDto> availabilityDtoList = availabilityList.stream()
                .map(AvailabilityMapper::mapToAvailabilityDto).toList();

        return availabilityDtoList;
    }

    @Override
    @Transactional
    public void updateAvailability(Long employeeId, AvailabilityDto availabilityDto) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
//        Availability currentAvailability = employee.getAvailabilityList()
        Availability currentAvailability = availabilityRepository.findByEmployeeAndDate(employee, availabilityDto.getDate()).orElseThrow(RuntimeException::new);

        Availability availability = AvailabilityMapper.mapToAvailability(availabilityDto, currentAvailability);
        employee.getAvailabilityList().remove(currentAvailability);
        employee.getAvailabilityList().add(availability);

        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void deleteAvailability(Long employeeId, AvailabilityDto availabilityDto) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Availability currentAvailability = availabilityRepository.findByEmployeeAndDate(employee, availabilityDto.getDate()).orElseThrow(RuntimeException::new);
        availabilityRepository.delete(currentAvailability);
        employee.getAvailabilityList().remove(currentAvailability);
        employeeRepository.save(employee);

    }
}
