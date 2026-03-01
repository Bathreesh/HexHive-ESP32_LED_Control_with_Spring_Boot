package com.example.hexhive.repository;

import com.example.hexhive.model.LedState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LedRepository extends JpaRepository<LedState, Long> {
}
