package com.michal.booksylikeapp.contoller.employeeRequests;

import com.michal.booksylikeapp.dto.DefaultWeekWorkHoursDto;
import com.michal.booksylikeapp.service.DefaultWeekWorkHoursService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("B/employee/employeeId={employeeId}/workday/defaultWorkHours")
@AllArgsConstructor
public class DefaultWeekWorkHoursController {

    private DefaultWeekWorkHoursService defaultWeekWorkHoursService;

    // CRUD
    // Create
    @PostMapping
    public ResponseEntity<DefaultWeekWorkHoursDto> setDefaultWorkHoursForWeekDays(@PathVariable Long employeeId,
                                                                                  @RequestBody DefaultWeekWorkHoursDto dto){

        DefaultWeekWorkHoursDto defaultWeekWorkHoursDto = defaultWeekWorkHoursService.setDefaultWeekWorkHours(employeeId, dto);

        return new ResponseEntity<>(defaultWeekWorkHoursDto, HttpStatus.CREATED);
    }

    // Read
    @GetMapping
    public ResponseEntity<DefaultWeekWorkHoursDto> readDefaultWorkHoursForWeekDays(@PathVariable Long employeeId){

        DefaultWeekWorkHoursDto defaultWeekWorkHoursDto = defaultWeekWorkHoursService.readDefaultWeekWorkHours(employeeId);

        return new ResponseEntity<>(defaultWeekWorkHoursDto, HttpStatus.OK);
    }
}
