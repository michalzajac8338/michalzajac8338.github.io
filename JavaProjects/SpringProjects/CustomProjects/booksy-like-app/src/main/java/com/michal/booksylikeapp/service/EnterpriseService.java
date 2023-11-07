package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.EnterpriseDto;

public interface EnterpriseService {
    EnterpriseDto createEnterprise(EnterpriseDto enterpriseDto);
    EnterpriseDto readEnterprise(Long id);
    EnterpriseDto updateEnterprise(Long id, EnterpriseDto enterpriseDto);
    void deleteEnterprise(Long id);
}
