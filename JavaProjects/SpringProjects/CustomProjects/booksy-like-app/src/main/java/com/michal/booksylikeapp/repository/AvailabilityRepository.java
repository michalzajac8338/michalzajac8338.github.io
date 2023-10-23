package com.michal.booksylikeapp.repository;

import com.michal.booksylikeapp.entity.Availability;
import com.michal.booksylikeapp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    Optional<Availability> findByEmployeeAndDate(Employee employee, LocalDate date);

}
