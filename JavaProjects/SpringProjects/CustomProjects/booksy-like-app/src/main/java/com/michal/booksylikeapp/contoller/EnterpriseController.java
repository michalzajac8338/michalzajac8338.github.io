package com.michal.booksylikeapp.contoller;

import com.michal.booksylikeapp.dto.ClientDto;
import com.michal.booksylikeapp.dto.EmployeeDto;
import com.michal.booksylikeapp.dto.EnterpriseDto;
import com.michal.booksylikeapp.service.EmployeeService;
import com.michal.booksylikeapp.service.EnterpriseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("B/enterprise")
@AllArgsConstructor
public class EnterpriseController {

    private EnterpriseService enterpriseService;
    private EmployeeService employeeService;

    // CRUD for enterprises & C for employee -> enterprise creates employee account

    // Create
    @PostMapping
    public ResponseEntity<EnterpriseDto> createEnterprise(@RequestBody EnterpriseDto enterpriseDto){

        EnterpriseDto createdEnterprise = enterpriseService.createEnterprise(enterpriseDto);
        return new ResponseEntity<>(createdEnterprise, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/id={id}")
    public ResponseEntity<EnterpriseDto> readEnterprise(@PathVariable Long id){

        EnterpriseDto queriedEnterprise = enterpriseService.readEnterprise(id);
        return new ResponseEntity<>(queriedEnterprise, HttpStatus.OK);
    }

    // Update
    @PutMapping("/id={id}")
    public ResponseEntity<EnterpriseDto> updateEnterprise(@PathVariable Long id,
                                                  @RequestBody EnterpriseDto enterpriseDto){

        EnterpriseDto updatedEnterprise = enterpriseService.updateEnterprise(id, enterpriseDto);
        return new ResponseEntity<>(updatedEnterprise, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/id={id}")
    public ResponseEntity<Void> deleteEnterprise(@PathVariable Long id){

        enterpriseService.deleteEnterprise(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Create EMPLOYEE
    @PostMapping("/id={id}/createEmployee")
    public ResponseEntity<EmployeeDto> createEmployee(@PathVariable("id") Long enterpriseId,
                                                      @RequestBody EmployeeDto employeeDto){

        employeeDto.setEnterpriseId(enterpriseId);
        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);

    }
}
