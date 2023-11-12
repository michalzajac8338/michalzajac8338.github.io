package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.constants.OtherConstants;
import com.michal.booksylikeapp.constants.VisitStatus;
import com.michal.booksylikeapp.dto.EmployeeDto;
import com.michal.booksylikeapp.entity.*;
import com.michal.booksylikeapp.mapper.EmployeeMapper;
import com.michal.booksylikeapp.mapper.ReviewMapper;
import com.michal.booksylikeapp.repository.*;
import com.michal.booksylikeapp.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.michal.booksylikeapp.constants.OtherConstants.TIME_SLOT_DURATION_IN_MIN;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EnterpriseRepository enterpriseRepository;
    private EmployeeRepository employeeRepository;
    private RoleRepository roleRepository;
    private ServiceRepository serviceRepository;
    private ReviewRepository reviewRepository;

    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto, null);
        employee.setRole(roleRepository.findByName("EMPLOYEE"));

        Enterprise enterprise = enterpriseRepository.findById(employeeDto.getEnterpriseId()).orElseThrow(RuntimeException::new);
        employee.setEnterprise(enterprise);

        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto readEmployee(Long enterpriseId, Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        List<Review> employeeReviews = reviewRepository.findByEmployee(employee);

        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);
        employeeDto.setReviews(employeeReviews.stream().map(ReviewMapper::mapToReviewDto).toList());

        return employeeDto;
    }

    @Override
    public EmployeeDto updateEmployee(Long enterpriseId, Long employeeId, EmployeeDto employeeDto) {

        Employee existingEmployee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Employee updatedEmployee = EmployeeMapper.mapToEmployee(employeeDto, existingEmployee);

        return EmployeeMapper.mapToEmployeeDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long enterpriseId, Long employeeId) {

        employeeRepository.deleteById(employeeId);
    }

    @Override
    public List<LocalDateTime> getAllPossibleVisitTimeForEmployee(Long employeeId, Long serviceId) {

        Employee queriedEmployee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Service service = serviceRepository.findById(serviceId).orElseThrow(RuntimeException::new);

        // get all valid visit hours (with enough time slots of employee to fulfill visit)
        List<LocalDateTime> validTimeSlots = queriedEmployee.getWorkdayList().stream()
                .sorted(Comparator.comparing(Workday::getDate)).map(
                workday ->  getAllValidVisitHours(workday, (int)service.getDuration().toSeconds()/60))
                .flatMap(Collection::stream).toList();

        return validTimeSlots;
    }

    // Helper methods
    public static List<LocalDateTime> getAllValidVisitHours(Workday workday, int visitDuration){
        List<LocalDateTime> allFreeTimeSlots = getAllAvailableTimeSlotsForADay(workday);
        return checkIfTimeSlotIsWideEnough(allFreeTimeSlots, visitDuration);
    }

    public static List<LocalDateTime> getAllAvailableTimeSlotsForADay(Workday workday){

        List<LocalDateTime> timeSlots = cutWorkday(workday.getWorkStartTime(), workday.getWorkEndTime());
        workday.getVisits().forEach(visit -> {
                    if (visit.getStatus() != VisitStatus.CANCELLED) {
                        LocalDateTime start = visit.getStartTime();
                        int numberOfTakenTimeSlots = (int) (visit.getService().getDuration().getSeconds()/60)/TIME_SLOT_DURATION_IN_MIN.getNumber();
                        while (numberOfTakenTimeSlots > 0) {
                            timeSlots.remove(start);
                            start = start.plusMinutes(TIME_SLOT_DURATION_IN_MIN.getNumber());
                            numberOfTakenTimeSlots--;
                        }
                    }
                }
        ); return timeSlots;}

    public static List<LocalDateTime> cutWorkday(LocalDateTime startTime, LocalDateTime endTime){

        List<LocalDateTime> timeSlots = new LinkedList<>();
        LocalDateTime timeSlot = startTime;

        while(timeSlot.isBefore(endTime)){
            timeSlots.add(timeSlot);
            timeSlot = timeSlot.plusMinutes(TIME_SLOT_DURATION_IN_MIN.getNumber());
        }

        return timeSlots;
    }

    public static List<LocalDateTime> checkIfTimeSlotIsWideEnough(List<LocalDateTime> timeSlots, int duration){

        List<LocalDateTime> wideEnoughTimeSlots = new LinkedList<>();
        int timeSlotsNeeded = duration/ TIME_SLOT_DURATION_IN_MIN.getNumber();

        for (int i=0; i<timeSlots.size()-timeSlotsNeeded+1;i++){

            var t1 = timeSlots.get(i).plusMinutes(duration- TIME_SLOT_DURATION_IN_MIN.getNumber());
            var t2 = timeSlots.get(i+timeSlotsNeeded-1);

            if(t1.equals(t2)){
                wideEnoughTimeSlots.add(timeSlots.get(i));
            }
        }

        return wideEnoughTimeSlots;
    }
}
