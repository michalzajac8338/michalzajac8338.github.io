package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.EnterpriseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface EnterpriseService {

    // CRUD
    EnterpriseDto createEnterprise(EnterpriseDto enterpriseDto);
    EnterpriseDto readEnterprise(Long id);
    EnterpriseDto updateEnterprise(Long id, EnterpriseDto enterpriseDto);
    void deleteEnterprise(Long id);

    // Additionally
    Map<Long, List<LocalDateTime>> getAllPossibleVisitTimeForEnterprise(Long enterpriseId, Long serviceId);

}
