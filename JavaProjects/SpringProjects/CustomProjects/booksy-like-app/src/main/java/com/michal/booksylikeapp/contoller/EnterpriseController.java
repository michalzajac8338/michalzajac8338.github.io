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

    // CRUD for enterprises

    // Create
    @PostMapping
    public ResponseEntity<EnterpriseDto> createEnterprise(@RequestBody EnterpriseDto enterpriseDto){

        EnterpriseDto createdEnterprise = enterpriseService.createEnterprise(enterpriseDto);
        return new ResponseEntity<>(createdEnterprise, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/enterpriseId={enterpriseId}")
    public ResponseEntity<EnterpriseDto> readEnterprise(@PathVariable Long enterpriseId){

        EnterpriseDto queriedEnterprise = enterpriseService.readEnterprise(enterpriseId);
        return new ResponseEntity<>(queriedEnterprise, HttpStatus.OK);
    }

    // Update
    @PutMapping("/enterpriseId={enterpriseId}")
    public ResponseEntity<EnterpriseDto> updateEnterprise(@PathVariable Long enterpriseId,
                                                          @RequestBody EnterpriseDto enterpriseDto){

        EnterpriseDto updatedEnterprise = enterpriseService.updateEnterprise(enterpriseId, enterpriseDto);
        return new ResponseEntity<>(updatedEnterprise, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/enterpriseId={enterpriseId}")
    public ResponseEntity<Void> deleteEnterprise(@PathVariable Long enterpriseId){

        enterpriseService.deleteEnterprise(enterpriseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
