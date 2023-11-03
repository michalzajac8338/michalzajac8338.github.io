package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.constants.VisitStatus;
import com.michal.booksylikeapp.dto.ClientDto;
import com.michal.booksylikeapp.dto.ClientVisitDto;
import com.michal.booksylikeapp.entity.Client;
import com.michal.booksylikeapp.entity.Employee;
import com.michal.booksylikeapp.entity.Visit;
import com.michal.booksylikeapp.entity.Workday;
import com.michal.booksylikeapp.mapper.ClientVisitMapper;
import com.michal.booksylikeapp.repository.ClientRepository;
import com.michal.booksylikeapp.repository.EmployeeRepository;
import com.michal.booksylikeapp.repository.VisitRepository;
import com.michal.booksylikeapp.repository.WorkdayRepository;
import com.michal.booksylikeapp.service.ClientVisitService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientVisitServiceImpl implements ClientVisitService {

    EmployeeRepository employeeRepository;
    ClientRepository clientRepository;
    VisitRepository visitRepository;
    WorkdayRepository workdayRepository;

    @Override
    @Transactional
    public ClientVisitDto createVisit(Long clientId, Long employeeId, ClientVisitDto clientVisitDto) {
        
        Visit visit = ClientVisitMapper.mapToVisit(clientVisitDto, null);

        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Workday workday = workdayRepository.findByEmployeeAndDate(employee, visit.getStartTime().toLocalDate())
                .orElseThrow(RuntimeException::new);

        visit.setClient(client);
        visit.setWorkday(workday);

        List<LocalDateTime> availableTimeSlots = EmployeeServiceImpl.getAllValidVisitHours(workday,clientVisitDto.getDurationInMin());

        if(availableTimeSlots.contains(visit.getStartTime())){

            Visit savedVisit = visitRepository.save(visit);
            return ClientVisitMapper.mapToClientVisitDto(savedVisit);

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

        return visits.stream().map(ClientVisitMapper::mapToClientVisitDto).toList();
    }
}
