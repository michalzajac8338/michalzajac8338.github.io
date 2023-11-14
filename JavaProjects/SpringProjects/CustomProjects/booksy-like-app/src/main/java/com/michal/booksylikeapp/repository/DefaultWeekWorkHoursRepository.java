package com.michal.booksylikeapp.repository;

import com.michal.booksylikeapp.entity.DefaultWeekWorkHours;
import com.michal.booksylikeapp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DefaultWeekWorkHoursRepository extends JpaRepository<DefaultWeekWorkHours, Long> {
    Set<DefaultWeekWorkHours> findByEmployee(Employee employee);
}
