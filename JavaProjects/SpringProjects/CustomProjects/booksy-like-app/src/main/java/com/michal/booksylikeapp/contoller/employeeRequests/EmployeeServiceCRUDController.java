package com.michal.booksylikeapp.contoller.employeeRequests;

import com.michal.booksylikeapp.dto.ServiceDto;
import com.michal.booksylikeapp.service.ServiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("B/employee/employeeId={employeeId}/service")
@AllArgsConstructor
public class EmployeeServiceCRUDController {

    private ServiceService service;

    // CRUD for services
    // Create
    @PostMapping
    public ResponseEntity<ServiceDto> addService(@PathVariable Long employeeId,
                                                 @RequestBody ServiceDto serviceDto){

        ServiceDto serviceDto1 = service.addService(employeeId, serviceDto);
        return new ResponseEntity<>(serviceDto1, HttpStatus.CREATED);
    }

    // Read (all your services)
    @GetMapping
    public ResponseEntity<Set<ServiceDto>> readEmployeeServices(@PathVariable Long employeeId){

        Set<ServiceDto> serviceDtos = service.readEmployeeServices(employeeId);
        return new ResponseEntity<>(serviceDtos, HttpStatus.OK);
    }

    // Update
    @PutMapping
    public ResponseEntity<ServiceDto> updateService(@PathVariable Long employeeId,
                                                    @RequestBody ServiceDto serviceDto){

        ServiceDto updatedService = service.updateService(employeeId, serviceDto);
        return new ResponseEntity<>(updatedService, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<Void> deleteService(@PathVariable Long employeeId,
                                              @RequestBody ServiceDto serviceDto){

        service.deleteService(employeeId, serviceDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
