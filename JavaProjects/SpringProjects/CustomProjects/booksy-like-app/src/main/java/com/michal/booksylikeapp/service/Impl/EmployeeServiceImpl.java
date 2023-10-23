package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.dto.ClientVisitDto;
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
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static com.michal.booksylikeapp.constants.OtherConstants.TIMESLOTDURATION_IN_MIN;
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EnterpriseRepository enterpriseRepository;
    private EmployeeRepository employeeRepository;
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Enterprise enterprise = enterpriseRepository.findById(employeeDto.getEnterpriseId()).orElseThrow(RuntimeException::new);
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto, null);
        employee.setRole(roleRepository.findByName("EMPLOYEE"));
        employee.setEnterprise(enterprise);
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDto savedEmployeeDto = EmployeeMapper.mapToEmployeeDto(savedEmployee);
        savedEmployeeDto.setEnterpriseId(employeeDto.getEnterpriseId());

        return savedEmployeeDto;
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
    public List<LocalDateTime> getAllPossibleVisitTime(Long employeeId, ClientVisitDto clientVisitDto) {

        Employee queriedEmployee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        List<Workday> workDays = queriedEmployee.getWorkdayList().stream().sorted(Comparator.comparing(Workday::getDate)).toList();

        // get available 15 min time slots of employee
        List<List<LocalDateTime>> availableTimeSlots = workDays.stream().map(workDay -> {

                    List<LocalDateTime> timeSlots = cutWorkday(workDay.getWorkStartTime(), workDay.getWorkEndTime());
                    workDay.getVisits().forEach(visit -> {
                                LocalDateTime start = visit.getStartTime();
                                int numberOfTakenTimeSlots = visit.getDuration().toMinutesPart()/ TIMESLOTDURATION_IN_MIN.getNumber();
                                while (numberOfTakenTimeSlots>0){
                                    timeSlots.remove(start);
                                    start = start.plusMinutes(TIMESLOTDURATION_IN_MIN.getNumber());
                                    numberOfTakenTimeSlots--;
                                }
                            }
                    ); return timeSlots;}).toList();

        // check if time slots are enough for visit duration
        int duration = clientVisitDto.getDuration();
        int timeSlotsNeeded = duration/TIMESLOTDURATION_IN_MIN.getNumber();

        List<LocalDateTime> allTimeSlots = availableTimeSlots.stream().flatMap(Collection::stream).toList();
        List<LocalDateTime> wideEnoughTimeSlots = new LinkedList<>();

        for (int i=0; i<allTimeSlots.size()-timeSlotsNeeded+1;i++){

            var t1 = allTimeSlots.get(i).plusMinutes(duration-TIMESLOTDURATION_IN_MIN.getNumber());
            var t2 = allTimeSlots.get(i+timeSlotsNeeded-1);

            if(t1.equals(t2)){
                wideEnoughTimeSlots.add(allTimeSlots.get(i));
            }
        }

        return wideEnoughTimeSlots;
    }

    // Helper methods
    private List<LocalDateTime> cutWorkday(LocalDateTime startTime, LocalDateTime endTime){

        List<LocalDateTime> timeSlots = new LinkedList<>();
        LocalDateTime timeSlot = startTime;

        while(timeSlot.isBefore(endTime)){
            timeSlots.add(timeSlot);
            timeSlot = timeSlot.plusMinutes(15);
        }

        return timeSlots;
    }

    private List<LocalDateTime> getPossibleVisitTimeForADay(Workday workday){return null;}
}
