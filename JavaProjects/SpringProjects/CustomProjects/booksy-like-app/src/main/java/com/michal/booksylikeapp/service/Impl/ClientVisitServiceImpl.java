package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.dto.ClientVisitDto;
import com.michal.booksylikeapp.repository.ClientRepository;
import com.michal.booksylikeapp.repository.EmployeeRepository;
import com.michal.booksylikeapp.repository.VisitRepository;
import com.michal.booksylikeapp.service.ClientService;
import com.michal.booksylikeapp.service.ClientVisitService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ClientVisitServiceImpl implements ClientVisitService {

    EmployeeRepository employeeRepository;
    ClientRepository clientRepository;
    VisitRepository visitRepository;

    @Override
    @Transactional
    public ClientVisitDto createVisit(Long clientId, Long employeeId, ClientVisitDto clientVisitDto) {




        return null;
    }
}
