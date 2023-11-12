package com.michal.booksylikeapp.repository;

import com.michal.booksylikeapp.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Duration;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByNameAndCostAndDuration(String name, Double cost, Duration duration);
}
