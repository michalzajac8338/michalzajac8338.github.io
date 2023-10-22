package com.michal.booksylikeapp.mapper;

import com.michal.booksylikeapp.dto.EnterpriseDto;
import com.michal.booksylikeapp.entity.Enterprise;

public class EnterpriseMapper {

    public static Enterprise mapToEnterprise(EnterpriseDto enterpriseDto, Enterprise enterprise){

        if(enterprise==null) {
            enterprise = new Enterprise();
        }

        enterprise.setName(enterpriseDto.getName());
        enterprise.setDescription(enterpriseDto.getDescription());
        enterprise.setEmail(enterpriseDto.getEmail());
        enterprise.setUsername(enterpriseDto.getUsername());
        enterprise.setPassword(enterpriseDto.getPassword());

        return enterprise;
    }

    public static EnterpriseDto mapToEnterpriseDto(Enterprise enterprise){

        EnterpriseDto enterpriseDto = new EnterpriseDto();

        enterpriseDto.setId(enterprise.getId());
        enterpriseDto.setName(enterprise.getName());
        enterpriseDto.setDescription(enterprise.getDescription());
        enterpriseDto.setEmail(enterprise.getEmail());
        enterpriseDto.setUsername(enterprise.getUsername());
        enterpriseDto.setPassword(enterprise.getPassword());

        return enterpriseDto;
    }

}
