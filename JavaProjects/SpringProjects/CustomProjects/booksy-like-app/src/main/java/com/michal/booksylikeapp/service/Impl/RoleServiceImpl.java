package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.entity.Role;
import com.michal.booksylikeapp.repository.RoleRepository;
import com.michal.booksylikeapp.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    @Override
    public void initializeRoles() {

        Role role = new Role();
        role.setName("CLIENT");
        Role role1 = new Role();
        role1.setName("EMPLOYEE");
        Role role2 = new Role();
        role2.setName("ENTERPRISE");

        roleRepository.saveAll(List.of(role,role1, role2));
    }
}
