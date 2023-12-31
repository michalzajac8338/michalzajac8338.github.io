package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.dto.EnterpriseDto;
import com.michal.booksylikeapp.entity.Enterprise;
import com.michal.booksylikeapp.mapper.EnterpriseMapper;
import com.michal.booksylikeapp.repository.EnterpriseRepository;
import com.michal.booksylikeapp.repository.RoleRepository;
import com.michal.booksylikeapp.service.EmployeeService;
import com.michal.booksylikeapp.service.EnterpriseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class EnterpriseServiceImpl implements EnterpriseService {

    private EmployeeServiceImpl employeeService;
    private EnterpriseRepository enterpriseRepository;
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public EnterpriseDto createEnterprise(EnterpriseDto enterpriseDto) {

        Enterprise enterprise = EnterpriseMapper.mapToEnterprise(enterpriseDto, null);
        enterprise.setRole(roleRepository.findByName("ENTERPRISE"));
        Enterprise savedEnterprise = enterpriseRepository.save(enterprise);

        return EnterpriseMapper.mapToEnterpriseDto(savedEnterprise);
    }

    @Override
    public EnterpriseDto readEnterprise(Long id) {

        Enterprise queriedEnterprise = enterpriseRepository.findById(id).orElseThrow(RuntimeException::new);
        return EnterpriseMapper.mapToEnterpriseDto(queriedEnterprise);
    }

    @Override
    public EnterpriseDto updateEnterprise(Long id, EnterpriseDto enterpriseDto) {

        Enterprise existingEnterprise = enterpriseRepository.findById(id).orElseThrow(RuntimeException::new);
        Enterprise updatedEnterprise = EnterpriseMapper.mapToEnterprise(enterpriseDto, existingEnterprise);

        return EnterpriseMapper.mapToEnterpriseDto(updatedEnterprise);
    }

    @Override
    public void deleteEnterprise(Long id) {

        enterpriseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Map<Long, List<LocalDateTime>> getAllPossibleVisitTimeForEnterprise(Long enterpriseId, Long serviceId) {

        Enterprise queriedEnterprise = enterpriseRepository.findById(enterpriseId).orElseThrow(RuntimeException::new);
        Map<Long, List<LocalDateTime>> employeeIdAndTimeSlots = new HashMap<>();

        queriedEnterprise.getEmployees().forEach(employee -> {
            employeeIdAndTimeSlots.put(employee.getId(),
                    employeeService.getAllPossibleVisitTimeForEmployee(employee.getId(), serviceId));
        });

        return employeeIdAndTimeSlots;
    }
}
