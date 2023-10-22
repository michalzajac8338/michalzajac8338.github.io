package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.dto.EmployeeDto;
import com.michal.booksylikeapp.entity.Employee;
import com.michal.booksylikeapp.entity.Enterprise;
import com.michal.booksylikeapp.mapper.EmployeeMapper;
import com.michal.booksylikeapp.repository.EmployeeRepository;
import com.michal.booksylikeapp.repository.EnterpriseRepository;
import com.michal.booksylikeapp.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EnterpriseRepository enterpriseRepository;
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Enterprise enterprise = enterpriseRepository.findById(employeeDto.getEnterpriseId()).orElseThrow(RuntimeException::new);
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto, null);
        employee.setEnterprise(enterprise);
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDto savedEmployeeDto = EmployeeMapper.mapToEmployeeDto(savedEmployee);
        savedEmployeeDto.setEnterpriseId(employeeDto.getEnterpriseId());

        return savedEmployeeDto;
    }
}
