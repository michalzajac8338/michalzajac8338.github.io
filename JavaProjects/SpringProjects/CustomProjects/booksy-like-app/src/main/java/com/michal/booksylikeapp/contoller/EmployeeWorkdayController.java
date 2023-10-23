package com.michal.booksylikeapp.contoller;

import com.michal.booksylikeapp.dto.WorkdayDto;
import com.michal.booksylikeapp.service.WorkdayService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("B/enterprise/enterpriseId={enterpriseId}/employee/employeeId={employeeId}")
@AllArgsConstructor
public class EmployeeWorkdayController {

    private WorkdayService workdayService;

    // additional functionality
    // Set global workday for employee
    // e.g. http://localhost:8080/B/enterprise/enterpriseId=1/employee/employeeId=1/workday
    // body
//    {
//        "date" : "2023-01-01",
//        "workStartTime" : "08:00",
//        "workEndTime" : "16:00"
//    }

    // CRUD for workdays
    // Create
    @PostMapping("workday")
    public ResponseEntity<Void> createWorkday(@PathVariable Long enterpriseId,
                                              @PathVariable Long employeeId,
                                              @RequestBody WorkdayDto workdayDto){

        workdayService.createWorkday(employeeId, workdayDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Read ->
    @GetMapping("workday")
    public ResponseEntity<List<WorkdayDto>> readAllSingleEmployeeWorkdaysForDuration(@PathVariable Long enterpriseId,
                                                                                     @PathVariable Long employeeId){

        List<WorkdayDto> workdayDtoList = workdayService.readWorkdayList(employeeId);
        return new ResponseEntity<>(workdayDtoList, HttpStatus.CREATED);
    }

    // Update
    @PutMapping("workday")
    public ResponseEntity<Void> updateWorkday(@PathVariable Long enterpriseId,
                                              @PathVariable Long employeeId,
                                              @RequestBody WorkdayDto workdayDto){

        workdayService.updateWorkday(employeeId, workdayDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Delete
    @DeleteMapping("workday")
    public ResponseEntity<Void> deleteWorkday(@PathVariable Long enterpriseId,
                                              @PathVariable Long employeeId,
                                              @RequestBody WorkdayDto workdayDto){

        workdayService.deleteWorkday(employeeId, workdayDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
