package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.dto.WorkdayDto;
import com.michal.booksylikeapp.entity.Employee;
import com.michal.booksylikeapp.entity.Workday;
import com.michal.booksylikeapp.mapper.WorkdayMapper;
import com.michal.booksylikeapp.repository.WorkdayRepository;
import com.michal.booksylikeapp.repository.EmployeeRepository;
import com.michal.booksylikeapp.service.WorkdayService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class WorkdayServiceImpl implements WorkdayService {

    private EmployeeRepository employeeRepository;
    private WorkdayRepository workdayRepository;

    @Override
    public WorkdayDto createWorkday(Long employeeId, WorkdayDto workdayDto) {

        Workday workday = WorkdayMapper.mapToWorkday(workdayDto, null);

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        employee.getWorkdayList().add(workday);
        employeeRepository.save(employee);

        return WorkdayMapper.mapToWorkdayDto(workday);
    }

    @Override
    public List<WorkdayDto> readWorkdayList(Long employeeId) {

        List<Workday> workdayList = employeeRepository.findById(employeeId)
                .orElseThrow(RuntimeException::new).getWorkdayList();

        List<WorkdayDto> workdayDtoList = workdayList.stream()
                .map(WorkdayMapper::mapToWorkdayDto).toList();

        return workdayDtoList;
    }

    @Override
    @Transactional
    public WorkdayDto updateWorkday(Long employeeId, WorkdayDto workdayDto) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Workday currentWorkday = workdayRepository.findByEmployeeAndDate(employee, LocalDate.parse(workdayDto.getDate())).orElseThrow(RuntimeException::new);

        Workday updatedWorkday = WorkdayMapper.mapToWorkday(workdayDto, currentWorkday);
        employee.getWorkdayList().remove(currentWorkday);
        employee.getWorkdayList().add(updatedWorkday);

        employeeRepository.save(employee);
        return WorkdayMapper.mapToWorkdayDto(updatedWorkday);
    }

    @Override
    @Transactional
    public void deleteWorkday(Long employeeId, WorkdayDto workdayDto) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Workday currentWorkday = workdayRepository.findByEmployeeAndDate(employee, LocalDate.parse(workdayDto.getDate())).orElseThrow(RuntimeException::new);
//        workdayRepository.delete(currentWorkday);
        employee.getWorkdayList().remove(currentWorkday);
        employeeRepository.save(employee);

    }
}
