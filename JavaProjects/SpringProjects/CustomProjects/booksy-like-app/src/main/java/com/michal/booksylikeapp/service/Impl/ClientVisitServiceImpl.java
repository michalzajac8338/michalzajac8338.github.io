package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.constants.VisitStatus;
import com.michal.booksylikeapp.dto.VisitDto;
import com.michal.booksylikeapp.entity.*;
import com.michal.booksylikeapp.mapper.VisitMapper;
import com.michal.booksylikeapp.repository.*;
import com.michal.booksylikeapp.service.ClientVisitService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ClientVisitServiceImpl implements ClientVisitService {

    EmployeeRepository employeeRepository;
    ClientRepository clientRepository;
    VisitRepository visitRepository;
    WorkdayRepository workdayRepository;
    ServiceRepository serviceRepository;

    @Override
    @Transactional
    public VisitDto createVisit(Long clientId, Long employeeId, VisitDto visitDto) {
        
        Visit visit = VisitMapper.mapToVisit(visitDto, null);
        Service service = serviceRepository.findById(visitDto.getServiceId()).orElseThrow(RuntimeException::new);

        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Workday workday = workdayRepository.findByEmployeeAndDate(employee, visit.getStartTime().toLocalDate())
                .orElseThrow(RuntimeException::new);

        visit.setClient(client);
        visit.setWorkday(workday);
        visit.setService(service);

        List<LocalDateTime> availableTimeSlots = EmployeeServiceImpl.getAllValidVisitHours(workday,
                (int) service.getDuration().toSeconds()/60);

        if(availableTimeSlots.contains(visit.getStartTime())){

            Visit savedVisit = visitRepository.save(visit);
            return VisitMapper.mapToVisitDto(savedVisit);

        } else {
            throw new RuntimeException();
        }

    }

    @Override
    public void cancelVisit(Long visitId) {

        Visit visitToCancel = visitRepository.findById(visitId).orElseThrow(RuntimeException::new);
        visitToCancel.setStatus(VisitStatus.CANCELLED);
        visitRepository.save(visitToCancel);

    }

    @Override
    public List<VisitDto> readAllVisitsForClient(Long clientId) {

        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);

        List<Visit> visits = client.getVisits().stream().sorted(Comparator.comparing(Visit::getStartTime)).toList();

        return visits.stream().map(VisitMapper::mapToVisitDto).toList();
    }

    @Override
    public VisitDto readVisitForClient(Long visitId) {
        Visit visit = visitRepository.findById(visitId).orElseThrow(RuntimeException::new);
        return VisitMapper.mapToVisitDto(visit);
    }
}
