package com.michal.booksylikeapp.repository;

import com.michal.booksylikeapp.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Service findByNameAndCost(String name, Double cost);
    Service findByNameAndCostAndDuration(String name, Double cost, Integer durationInMin);
}
