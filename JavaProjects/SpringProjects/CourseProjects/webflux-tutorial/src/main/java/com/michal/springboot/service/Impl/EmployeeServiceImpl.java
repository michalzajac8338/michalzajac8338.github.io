package com.michal.springboot.service.Impl;

import com.michal.springboot.dto.EmployeeDto;
import com.michal.springboot.entity.Employee;
import com.michal.springboot.mapper.EmployeeMapper;
import com.michal.springboot.repository.EmployeeRepository;
import com.michal.springboot.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        Mono<Employee> savedEmployee = employeeRepository.save(employee);

        Mono<EmployeeDto> employeeDtoMono = savedEmployee
                .map(employeeEntity -> EmployeeMapper.mapToEmployeeDto(employeeEntity));

        return employeeDtoMono;
    }

    @Override
    public Mono<EmployeeDto> getEmployee(String employeeId) {

        Mono<Employee> savedEmployee = employeeRepository.findById(employeeId);

        return savedEmployee.map(employee -> EmployeeMapper.mapToEmployeeDto(employee));
    }

    @Override
    public Flux<EmployeeDto> getAllEmployees() {

        Flux<Employee> employees = employeeRepository.findAll();

        return employees.map(employee -> EmployeeMapper.mapToEmployeeDto(employee))
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDto> updateEmployee(EmployeeDto employeeDto, String id) {

        Mono<Employee> employee = employeeRepository.findById(id);

        Mono<Employee> updatedEmployee = employee.flatMap(existingEmployee -> {
            existingEmployee.setFirstName(employeeDto.getFirstName());
            existingEmployee.setLastName(employeeDto.getLastName());
            existingEmployee.setEmail(employeeDto.getEmail());

            return employeeRepository.save(existingEmployee);
        });

        return updatedEmployee.map(employee1 -> EmployeeMapper.mapToEmployeeDto(employee1));
    }

    @Override
    public Mono<Void> deleteEmployee(String id) {

        return employeeRepository.deleteById(id);

    }
}
