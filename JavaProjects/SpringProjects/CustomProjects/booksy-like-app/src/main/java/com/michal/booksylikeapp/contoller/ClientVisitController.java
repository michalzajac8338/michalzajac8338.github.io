package com.michal.booksylikeapp.contoller;

import com.michal.booksylikeapp.dto.ClientVisitDto;
import com.michal.booksylikeapp.entity.Visit;
import com.michal.booksylikeapp.service.ClientVisitService;
import com.michal.booksylikeapp.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("B/client/clientId={clientId}/visit")
@AllArgsConstructor
public class ClientVisitController {

    private EmployeeService employeeService;
    private ClientVisitService clientVisitService;

    // Client CRUD Visits
    // Create
    @PostMapping
    public ResponseEntity<Visit> createVisit(@PathVariable Long clientId,
                                             @RequestBody Visit visit){

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Read - chosen employee
    // e.g. B/client/clientId=1/visit/employee/employeeId=1
    // body
//    {
//        "duration" : "01:15",
//        "type" : "learning java"
//    }
    @GetMapping("/employee/employeeId={employeeId}")
    public ResponseEntity<List<LocalDateTime>> readEmployeeCalendar(@PathVariable Long clientId,
                                                                    @PathVariable Long employeeId,
                                                                    @RequestBody ClientVisitDto clientVisitDto){

        List<LocalDateTime> availableTimeSlots = employeeService.getPossibleVisitTime(employeeId, clientVisitDto);

        return new ResponseEntity<>(availableTimeSlots, HttpStatus.OK);
    }
}
