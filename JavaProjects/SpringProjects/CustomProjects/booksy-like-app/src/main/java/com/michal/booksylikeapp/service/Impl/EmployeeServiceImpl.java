package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.constants.VisitStatus;
import com.michal.booksylikeapp.dto.EmployeeDto;
import com.michal.booksylikeapp.entity.Employee;
import com.michal.booksylikeapp.entity.Enterprise;
import com.michal.booksylikeapp.entity.Workday;
import com.michal.booksylikeapp.mapper.EmployeeMapper;
import com.michal.booksylikeapp.repository.EmployeeRepository;
import com.michal.booksylikeapp.repository.EnterpriseRepository;
import com.michal.booksylikeapp.repository.RoleRepository;
import com.michal.booksylikeapp.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static com.michal.booksylikeapp.constants.OtherConstants.TIMESLOTDURATION_IN_MIN;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EnterpriseRepository enterpriseRepository;
    private EmployeeRepository employeeRepository;
    private RoleRepository roleRepository;

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
        return EmployeeMapper.mapToEmployeeDto(employee);
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
    public List<LocalDateTime> getAllPossibleVisitTime(Long employeeId, int visitDuration) {

        Employee queriedEmployee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);

        // get all valid visit hours (with enough time slots of employee to fulfill visit)
        List<LocalDateTime> validTimeSlots = queriedEmployee.getWorkdayList().stream()
                .sorted(Comparator.comparing(Workday::getDate)).map(
                workday ->  getAllValidVisitHours(workday, visitDuration)).flatMap(Collection::stream).toList();

        return validTimeSlots;
    }

    public static List<LocalDateTime> getAllValidVisitHours(Workday workday, int visitDuration){
        List<LocalDateTime> allFreeTimeSlots = getAllAvailableTimeSlotsForADay(workday);
        return checkIfTimeSlotIsWideEnough(allFreeTimeSlots, visitDuration);
    }

    // Helper methods

    public static List<LocalDateTime> getAllAvailableTimeSlotsForADay(Workday workday){

        List<LocalDateTime> timeSlots = cutWorkday(workday.getWorkStartTime(), workday.getWorkEndTime());
        workday.getVisits().forEach(visit -> {
                    if (visit.getStatus() != VisitStatus.CANCELLED) {
                        LocalDateTime start = visit.getStartTime();
                        int numberOfTakenTimeSlots = (int) (visit.getDuration().getSeconds() / 60) / TIMESLOTDURATION_IN_MIN.getNumber();
                        while (numberOfTakenTimeSlots > 0) {
                            timeSlots.remove(start);
                            start = start.plusMinutes(TIMESLOTDURATION_IN_MIN.getNumber());
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
            timeSlot = timeSlot.plusMinutes(15);
        }

        return timeSlots;
    }

    public static List<LocalDateTime> checkIfTimeSlotIsWideEnough(List<LocalDateTime> timeSlots, int duration){

        List<LocalDateTime> wideEnoughTimeSlots = new LinkedList<>();
        int timeSlotsNeeded = duration/TIMESLOTDURATION_IN_MIN.getNumber();

        for (int i=0; i<timeSlots.size()-timeSlotsNeeded+1;i++){

            var t1 = timeSlots.get(i).plusMinutes(duration-TIMESLOTDURATION_IN_MIN.getNumber());
            var t2 = timeSlots.get(i+timeSlotsNeeded-1);

            if(t1.equals(t2)){
                wideEnoughTimeSlots.add(timeSlots.get(i));
            }
        }

        return wideEnoughTimeSlots;

    }
}
