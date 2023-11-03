package com.michal.booksylikeapp.contoller;

import com.michal.booksylikeapp.dto.WorkdayDto;
import com.michal.booksylikeapp.service.WorkdayService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("B/enterprise/enterpriseId={enterpriseId}/employee/employeeId={employeeId}/workday")
@AllArgsConstructor
public class WorkdayCRUDController {

    private WorkdayService workdayService;

    // additional functionality
    // Set global workday for employee
    // e.g. http://localhost:8080/B/enterprise/enterpriseId=1/employee/employeeId=1/workday
    // body
//    {
//        "date" : "2023-01-01",
//        "workStartTime" : "2023-01-01T10:00",
//        "workEndTime" : "2023-01-01T16:00"
//    }

    // CRUD for workdays
    // Create
    @PostMapping
    public ResponseEntity<WorkdayDto> createWorkday(@PathVariable Long enterpriseId,
                                              @PathVariable Long employeeId,
                                              @RequestBody WorkdayDto workdayDto){

        WorkdayDto createdWorkday = workdayService.createWorkday(employeeId, workdayDto);
        return new ResponseEntity<>(createdWorkday, HttpStatus.CREATED);
    }

    // Read ->
    @GetMapping
    public ResponseEntity<List<WorkdayDto>> readAllSingleEmployeeWorkdaysForDuration(@PathVariable Long enterpriseId,
                                                                                     @PathVariable Long employeeId){

        List<WorkdayDto> workdayDtoList = workdayService.readWorkdayList(employeeId);
        return new ResponseEntity<>(workdayDtoList, HttpStatus.OK);
    }

    // Update
    @PutMapping
    public ResponseEntity<WorkdayDto> updateWorkday(@PathVariable Long enterpriseId,
                                                    @PathVariable Long employeeId,
                                                    @RequestBody WorkdayDto workdayDto){

        WorkdayDto updatedWorkday = workdayService.updateWorkday(employeeId, workdayDto);
        return new ResponseEntity<>(updatedWorkday, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<Void> deleteWorkday(@PathVariable Long enterpriseId,
                                              @PathVariable Long employeeId,
                                              @RequestBody WorkdayDto workdayDto){

        workdayService.deleteWorkday(employeeId, workdayDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
