package com.michal.booksylikeapp.repository;

import com.michal.booksylikeapp.entity.Employee;
import com.michal.booksylikeapp.entity.Workday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WorkdayRepository extends JpaRepository<Workday, Long> {
    Optional<Workday> findByEmployeeAndDate(Employee employee, LocalDate date);

}
