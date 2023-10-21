package com.michal.booksylikeapp.repository;

import com.michal.booksylikeapp.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
