package com.michal.booksylikeapp.contoller.clientRequests;
// Visits CRUD by Client
// Additionally R of all client's visits
// Additionally R of possible visit time for employee/ enterprise

import com.michal.booksylikeapp.dto.ClientVisitDto;
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
public class VisitController {

    private EmployeeService employeeService;
    private ClientVisitService clientVisitService;

    // CRUD
    // Create
    @PostMapping("/employee/employeeId={employeeId}")
    public ResponseEntity<ClientVisitDto> createVisit(@PathVariable Long clientId,
                                                      @PathVariable Long employeeId,
                                                      @RequestBody ClientVisitDto clientVisitDto){

        ClientVisitDto createdVisit = clientVisitService.createVisit(clientId, employeeId, clientVisitDto);
        return new ResponseEntity<>(createdVisit, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("visitId={visitId}")
    public ResponseEntity<ClientVisitDto> readVisit(@PathVariable Long clientId,
                                                    @PathVariable Long visitId){

        ClientVisitDto clientVisitDto = clientVisitService.readVisitForClient(visitId);
        return new ResponseEntity<>(clientVisitDto, HttpStatus.OK);
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
    public ResponseEntity<List<ClientVisitDto>> readVisitsForCurrentClient(@PathVariable Long clientId){

        List<ClientVisitDto> visitsForClient = clientVisitService.readVisitsForClient(clientId);
        return new ResponseEntity<>(visitsForClient, HttpStatus.OK);
    }

    // Read possible visit hours (for set duration) for chosen employee
    // e.g. body
//    {
//        "duration" : "01:15",
//        "type" : "learning java"
//    }
    @GetMapping("/employee/employeeId={employeeId}")
    public ResponseEntity<List<LocalDateTime>> readAllSingleEmployeeTimeSlotsForDuration(@PathVariable Long clientId,
                                                                                         @PathVariable Long employeeId,
                                                                                         @RequestBody ClientVisitDto clientVisitDto){

        List<LocalDateTime> availableTimeSlots = employeeService.getAllPossibleVisitTime(employeeId, clientVisitDto.getDurationInMin());
        return new ResponseEntity<>(availableTimeSlots, HttpStatus.OK);
    }

}
