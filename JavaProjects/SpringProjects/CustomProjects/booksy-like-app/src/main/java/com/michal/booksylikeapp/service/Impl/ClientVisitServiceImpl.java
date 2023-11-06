package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.constants.VisitStatus;
import com.michal.booksylikeapp.dto.ClientVisitDto;
import com.michal.booksylikeapp.entity.*;
import com.michal.booksylikeapp.mapper.ClientVisitMapper;
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
    public ClientVisitDto createVisit(Long clientId, Long employeeId, ClientVisitDto clientVisitDto) {
        
        Visit visit = ClientVisitMapper.mapToVisit(clientVisitDto, null);
        Service service = serviceRepository.findByNameAndCost(clientVisitDto.getName(), clientVisitDto.getCost()) ;

        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Workday workday = workdayRepository.findByEmployeeAndDate(employee, visit.getStartTime().toLocalDate())
                .orElseThrow(RuntimeException::new);

        visit.setClient(client);
        visit.setWorkday(workday);
        visit.setService(service);

        List<LocalDateTime> availableTimeSlots = EmployeeServiceImpl.getAllValidVisitHours(workday,clientVisitDto.getDurationInMin());

        if(availableTimeSlots.contains(visit.getStartTime())){

            Visit savedVisit = visitRepository.save(visit);
            return ClientVisitMapper.mapToClientVisitDto(savedVisit, service);

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
    public List<ClientVisitDto> readVisitsForClient(Long clientId) {

        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);

        List<Visit> visits = client.getVisits().stream().sorted(Comparator.comparing(Visit::getStartTime)).toList();

        return visits.stream().map(visit -> ClientVisitMapper.mapToClientVisitDto(visit, visit.getService())).toList();
    }

    @Override
    public ClientVisitDto readVisitForClient(Long visitId) {
        Visit visit = visitRepository.findById(visitId).orElseThrow(RuntimeException::new);
        return ClientVisitMapper.mapToClientVisitDto(visit, visit.getService());
    }
}
