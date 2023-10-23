package com.michal.booksylikeapp.contoller;

import com.michal.booksylikeapp.dto.AvailabilityDto;
import com.michal.booksylikeapp.service.AvailabilityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("B/enterprise/enterpriseId={enterpriseId}/employee/employeeId={employeeId}")
@AllArgsConstructor
public class EmployeeAvailabilityController {

    private AvailabilityService availabilityService;

    // additional functionality
    // Set global availability for employee
    // e.g. http://localhost:8080/B/enterprise/enterpriseId=1/employee/employeeId=1/availability
    // body
//    {
//        "date" : "2023-01-01",
//        "workStartTime" : "08:00",
//        "workEndTime" : "16:00"
//    }

    // CRUD for availabilities
    // Create
    @PostMapping("availability")
    public ResponseEntity<Void> createAvailability(@PathVariable Long enterpriseId,
                                                   @PathVariable Long employeeId,
                                                   @RequestBody AvailabilityDto availabilityDto){

        availabilityService.createAvailability(employeeId, availabilityDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Read
    @GetMapping("availability")
    public ResponseEntity<List<AvailabilityDto>> readAllSingleEmployeeAvailabilitiesForDuration(@PathVariable Long enterpriseId,
                                                                                                @PathVariable Long employeeId){

        List<AvailabilityDto> availabilityDtoList = availabilityService.readAvailabilityList(employeeId);
        return new ResponseEntity<>(availabilityDtoList, HttpStatus.CREATED);
    }

    // Update
    @PutMapping("availability")
    public ResponseEntity<Void> updateAvailability(@PathVariable Long enterpriseId,
                                                   @PathVariable Long employeeId,
                                                   @RequestBody AvailabilityDto availabilityDto){

        availabilityService.updateAvailability(employeeId, availabilityDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Delete
    @DeleteMapping("availability")
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long enterpriseId,
                                                   @PathVariable Long employeeId,
                                                   @RequestBody AvailabilityDto availabilityDto){

        availabilityService.deleteAvailability(employeeId, availabilityDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
