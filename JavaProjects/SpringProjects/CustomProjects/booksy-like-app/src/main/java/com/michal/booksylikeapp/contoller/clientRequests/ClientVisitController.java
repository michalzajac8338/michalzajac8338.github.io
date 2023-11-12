package com.michal.booksylikeapp.contoller.clientRequests;
// Visits CRUD by Client
// Additionally R of all client's visits
// Additionally R of possible visit time for employee/ enterprise

import com.michal.booksylikeapp.dto.ServiceDto;
import com.michal.booksylikeapp.dto.VisitDto;
import com.michal.booksylikeapp.service.ClientVisitService;
import com.michal.booksylikeapp.service.EmployeeService;
import com.michal.booksylikeapp.service.EnterpriseService;
import com.michal.booksylikeapp.service.ServiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("B/client/clientId={clientId}/visit")
@AllArgsConstructor
public class ClientVisitController {

    private ClientVisitService clientVisitService;
    private EnterpriseService enterpriseService;
    private EmployeeService employeeService;
    private ServiceService serviceService;

    // CRUD
    // Create
    @PostMapping("/employee/employeeId={employeeId}")
    public ResponseEntity<VisitDto> createVisit(@PathVariable Long clientId,
                                                @PathVariable Long employeeId,
                                                @RequestBody VisitDto visitDto){

        VisitDto createdVisit = clientVisitService.createVisit(clientId, employeeId, visitDto);
        return new ResponseEntity<>(createdVisit, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("visitId={visitId}")
    public ResponseEntity<VisitDto> readVisit(@PathVariable Long clientId,
                                              @PathVariable Long visitId){

        VisitDto visitDto = clientVisitService.readVisitForClient(visitId);
        return new ResponseEntity<>(visitDto, HttpStatus.OK);
    }

    // Update -> pointless; have to cancel & create new one :)
    // Delete (Cancel)

    @DeleteMapping("/visitId={visitId}")
    public ResponseEntity<Void> cancelVisitById(@PathVariable Long clientId,
                                                @PathVariable Long visitId){

        clientVisitService.cancelVisit(visitId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Additional functionality
    // Read your visits
    @GetMapping
    public ResponseEntity<List<VisitDto>> readVisitsForCurrentClient(@PathVariable Long clientId){

        List<VisitDto> visitsForClient = clientVisitService.readAllVisitsForClient(clientId);
        return new ResponseEntity<>(visitsForClient, HttpStatus.OK);
    }

    // Read services for employee
    @GetMapping("/employee/employeeId={employeeId}")
    public ResponseEntity<Set<ServiceDto>> readEmployeeServices(@PathVariable Long clientId,
                                                                @PathVariable Long employeeId){

        Set<ServiceDto> serviceDtos = serviceService.readEmployeeServices(employeeId);
        return new ResponseEntity<>(serviceDtos, HttpStatus.OK);
    }

    // Read services for enterprise
    @GetMapping("/enterprise/enterpriseId={enterpriseId}")
    public ResponseEntity<Set<ServiceDto>> readServicesForEnterprise(@PathVariable Long clientId,
                                                                     @PathVariable Long enterpriseId){

        Set<ServiceDto> services = serviceService.readEnterpriseServices(enterpriseId);

        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    // Read all possible visit time for chosen service (duration) & employee
    @GetMapping("/employee/employeeId={employeeId}/availableTimeSlots")
    public ResponseEntity<List<LocalDateTime>> readAllSingleEmployeeTimeSlotsForDuration(@PathVariable Long clientId,
                                                                                         @PathVariable Long employeeId,
                                                                                         @RequestBody ServiceDto serviceDto){

        List<LocalDateTime> availableTimeSlots = employeeService.getAllPossibleVisitTimeForEmployee(employeeId, serviceDto.getId());
        return new ResponseEntity<>(availableTimeSlots, HttpStatus.OK);
    }

    // Read all possible visit time for chosen service (duration) & enterprise
    @GetMapping("/enterprise/enterpriseId={enterpriseId}/availableTimeSlots")
    public ResponseEntity<Map<Long, List<LocalDateTime>>> readEnterpriseTimeSlotsForDuration(@PathVariable Long clientId,
                                                                                             @PathVariable Long enterpriseId,
                                                                                             @RequestBody ServiceDto serviceDto){

        Map<Long, List<LocalDateTime>> employeeIdAndTimeSlots =
                enterpriseService.getAllPossibleVisitTimeForEnterprise(enterpriseId, serviceDto.getId());

        return new ResponseEntity<>(employeeIdAndTimeSlots, HttpStatus.OK);
    }
}
