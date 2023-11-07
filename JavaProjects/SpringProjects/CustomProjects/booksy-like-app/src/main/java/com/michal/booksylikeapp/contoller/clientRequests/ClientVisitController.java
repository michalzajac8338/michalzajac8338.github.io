package com.michal.booksylikeapp.contoller.clientRequests;
// Visits CRUD by Client
// Additionally R of all client's visits
// Additionally R of possible visit time for employee/ enterprise

import com.michal.booksylikeapp.dto.VisitDto;
import com.michal.booksylikeapp.service.ClientVisitService;
import com.michal.booksylikeapp.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("B/client/clientId={clientId}/visit")
@AllArgsConstructor
public class ClientVisitController {

    private EmployeeService employeeService;
    private ClientVisitService clientVisitService;

    // CRUD
    // Create
    @PostMapping("/employee/employeeId={employeeId}")
    public ResponseEntity<VisitDto> createVisit(@PathVariable Long clientId,
                                                @PathVariable Long employeeId,
                                                @RequestBody VisitDto visitDto){

        VisitDto createdVisit = clientVisitService.createVisit(clientId, employeeId, visitDto);
        return new ResponseEntity<>(createdVisit, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("visitId={visitId}")
    public ResponseEntity<VisitDto> readVisit(@PathVariable Long clientId,
                                              @PathVariable Long visitId){

        VisitDto visitDto = clientVisitService.readVisitForClient(visitId);
        return new ResponseEntity<>(visitDto, HttpStatus.OK);
    }

    // Update -> pointless; have to cancel & create new one :)
    // Delete (Cancel)

    @DeleteMapping("/visitId={visitId}")
    public ResponseEntity<Void> cancelVisitById(@PathVariable Long clientId,
                                                @PathVariable Long visitId){

        clientVisitService.cancelVisit(visitId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Read your visits
    @GetMapping
    public ResponseEntity<List<VisitDto>> readVisitsForCurrentClient(@PathVariable Long clientId){

        List<VisitDto> visitsForClient = clientVisitService.readVisitsForClient(clientId);
        return new ResponseEntity<>(visitsForClient, HttpStatus.OK);
    }

    @GetMapping("/employee/employeeId={employeeId}")
    public ResponseEntity<List<LocalDateTime>> readAllSingleEmployeeTimeSlotsForDuration(@PathVariable Long clientId,
                                                                                         @PathVariable Long employeeId,
                                                                                         @RequestBody VisitDto visitDto){

        List<LocalDateTime> availableTimeSlots = employeeService.getAllPossibleVisitTime(employeeId, visitDto.getServiceId());
        return new ResponseEntity<>(availableTimeSlots, HttpStatus.OK);
    }

}
