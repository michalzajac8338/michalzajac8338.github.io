package com.michal.booksylikeapp.contoller;

import com.michal.booksylikeapp.dto.EmployeeDto;
import com.michal.booksylikeapp.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("B/enterprise/enterpriseId={enterpriseId}/employee")
@AllArgsConstructor
public class EmployeeCRUDController {

    private EmployeeService employeeService;

    // CRUD for employees
    // Create
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@PathVariable Long enterpriseId,
                                                      @RequestBody EmployeeDto employeeDto){

        employeeDto.setEnterpriseId(enterpriseId);
        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);

    }

    // Read
    @GetMapping("/employeeId={employeeId}")
    public ResponseEntity<EmployeeDto> readEmployee(@PathVariable Long enterpriseId,
                                                    @PathVariable Long employeeId){

        EmployeeDto queriedEmployee = employeeService.readEmployee(enterpriseId, employeeId);
        return new ResponseEntity<>(queriedEmployee, HttpStatus.OK);
    }

    // Update
    @PutMapping("/employeeId={employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long enterpriseId,
                                                      @PathVariable Long employeeId,
                                                      @RequestBody EmployeeDto employeeDto){

        EmployeeDto updatedEmployee = employeeService.updateEmployee(enterpriseId, employeeId, employeeDto);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/employeeId={employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long enterpriseId,
                                               @PathVariable Long employeeId){

        employeeService.deleteEmployee(enterpriseId, employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
