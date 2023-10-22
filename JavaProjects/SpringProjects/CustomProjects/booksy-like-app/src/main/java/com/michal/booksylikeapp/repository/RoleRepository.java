package com.michal.booksylikeapp.repository;

import com.michal.booksylikeapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String client);
}
