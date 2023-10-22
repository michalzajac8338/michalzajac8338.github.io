package com.michal.booksylikeapp.mapper;

import com.michal.booksylikeapp.dto.ClientDto;
import com.michal.booksylikeapp.dto.EmployeeDto;
import com.michal.booksylikeapp.entity.Client;
import com.michal.booksylikeapp.entity.Employee;

public class EmployeeMapper {

        public static Employee mapToEmployee(EmployeeDto employeeDto, Employee employee){

        // when creating
        if(employee == null) {
            employee = new Employee();
        }

        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setUsername(employeeDto.getUsername());
        employee.setPassword(employeeDto.getPassword());

        return employee;
    }

    public static EmployeeDto mapToEmployeeDto(Employee employee){

        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setUsername(employee.getUsername());
        employeeDto.setPassword(employee.getPassword());

        return employeeDto;
    }




}
