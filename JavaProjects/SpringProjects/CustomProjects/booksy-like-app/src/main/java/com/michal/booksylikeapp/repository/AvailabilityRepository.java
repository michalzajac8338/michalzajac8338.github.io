package com.michal.booksylikeapp.repository;

import com.michal.booksylikeapp.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
}
