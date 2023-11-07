package com.michal.booksylikeapp.contoller.employeeRequests;

import com.michal.booksylikeapp.dto.VisitDto;
import com.michal.booksylikeapp.service.EmployeeVisitService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("B/employee/employeeId={employeeId}/visit")
@AllArgsConstructor
public class EmployeeVisitController {

    private EmployeeVisitService employeeVisitService;

    // RUD for visits by employee (D = CANCELLED/ NOT_ATTENDED)
    // Read all your visits
    @GetMapping
    public ResponseEntity<List<VisitDto>> readEmployeeVisits(@PathVariable Long employeeId){

        List<VisitDto> visits = employeeVisitService.readEmployeeVisits(employeeId);
        return new ResponseEntity<>(visits, HttpStatus.OK);
    }

    // Update("Delete") - confirm/ cancel/ set not attended
    @PatchMapping
    public ResponseEntity<VisitDto> updateVisit(@PathVariable Long employeeId,
                                                @RequestBody VisitDto visitDto){

        VisitDto updatedVisit = employeeVisitService.changeVisitStatus(employeeId, visitDto);
        return new ResponseEntity<>(updatedVisit, HttpStatus.OK);
    }

}
