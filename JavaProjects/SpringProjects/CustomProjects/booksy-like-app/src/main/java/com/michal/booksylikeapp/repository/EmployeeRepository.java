package com.michal.booksylikeapp.repository;

import com.michal.booksylikeapp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
