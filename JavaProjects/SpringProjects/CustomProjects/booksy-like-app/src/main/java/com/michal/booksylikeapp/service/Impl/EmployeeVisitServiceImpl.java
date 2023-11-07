package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.constants.VisitStatus;
import com.michal.booksylikeapp.dto.VisitDto;
import com.michal.booksylikeapp.entity.Visit;
import com.michal.booksylikeapp.entity.Workday;
import com.michal.booksylikeapp.mapper.VisitMapper;
import com.michal.booksylikeapp.repository.EmployeeRepository;
import com.michal.booksylikeapp.repository.VisitRepository;
import com.michal.booksylikeapp.service.EmployeeVisitService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeVisitServiceImpl implements EmployeeVisitService {
    private EmployeeRepository employeeRepository;
    private VisitRepository visitRepository;
    @Override
    public List<VisitDto> readEmployeeVisits(Long employeeId) {

        List<Workday> workdayList = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new).getWorkdayList();
        List<Visit> visits = new LinkedList<>();
        workdayList.forEach(workday -> visits.addAll(workday.getVisits()));

        return visits.stream().map(VisitMapper::mapToVisitDto).toList();
    }

    @Override
    public VisitDto changeVisitStatus(Long employeeId, VisitDto visitDto) {

        Visit visit = visitRepository.findById(visitDto.getId()).orElseThrow(RuntimeException::new);
        visit.setStatus(VisitStatus.valueOf(visitDto.getStatus()));
        Visit updatedVisit = visitRepository.save(visit);

        return VisitMapper.mapToVisitDto(updatedVisit);
    }
}
