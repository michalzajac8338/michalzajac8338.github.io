package com.michal.booksylikeapp.contoller.employeeRequests;

import com.michal.booksylikeapp.dto.WorkdayDto;
import com.michal.booksylikeapp.service.WorkdayService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("B/employee/employeeId={employeeId}/workday")
@AllArgsConstructor
public class WorkdayCRUDController {

    private WorkdayService workdayService;

    // CRUD for workdays
    // Create
    @PostMapping
    public ResponseEntity<WorkdayDto> createWorkday(@PathVariable Long employeeId,
                                                    @RequestBody WorkdayDto workdayDto){

        WorkdayDto createdWorkday = workdayService.createWorkday(employeeId, workdayDto);
        return new ResponseEntity<>(createdWorkday, HttpStatus.CREATED);
    }

    // Read ->
    @GetMapping
    public ResponseEntity<List<WorkdayDto>> readAllSingleEmployeeWorkdaysForDuration(@PathVariable Long employeeId){

        List<WorkdayDto> workdayDtoList = workdayService.readWorkdayList(employeeId);
        return new ResponseEntity<>(workdayDtoList, HttpStatus.OK);
    }

    // Update
    @PutMapping
    public ResponseEntity<WorkdayDto> updateWorkday(@PathVariable Long employeeId,
                                                    @RequestBody WorkdayDto workdayDto){

        WorkdayDto updatedWorkday = workdayService.updateWorkday(employeeId, workdayDto);
        return new ResponseEntity<>(updatedWorkday, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<Void> deleteWorkday(@PathVariable Long employeeId,
                                              @RequestBody WorkdayDto workdayDto){

        workdayService.deleteWorkday(employeeId, workdayDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
