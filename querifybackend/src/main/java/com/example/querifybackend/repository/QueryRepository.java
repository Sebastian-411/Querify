package com.example.querifybackend.repository;

import com.example.querifybackend.model.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for handling queries.
 */
@Repository
public interface QueryRepository extends JpaRepository<Query, Long> {
}
