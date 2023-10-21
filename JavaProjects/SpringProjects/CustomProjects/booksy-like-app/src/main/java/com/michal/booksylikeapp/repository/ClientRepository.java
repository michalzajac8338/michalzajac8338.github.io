package com.michal.booksylikeapp.repository;

import com.michal.booksylikeapp.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
