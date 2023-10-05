package com.michal.springboot.controller;

import com.michal.springboot.dto.EmployeeDto;
import com.michal.springboot.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    //build reactive save

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){

        return employeeService.saveEmployee(employeeDto);
    }

    @GetMapping("/{employeeId}")
    public Mono<EmployeeDto> getEmployee(@PathVariable("employeeId") String employeeId){

        return employeeService.getEmployee(employeeId);

    }

    @GetMapping
    public Flux<EmployeeDto> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    public Mono<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto,
                                            @PathVariable String id){
        return employeeService.updateEmployee(employeeDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEmployee(@PathVariable String id){

        return employeeService.deleteEmployee(id);
    }
}
