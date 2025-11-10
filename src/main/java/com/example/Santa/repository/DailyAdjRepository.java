package com.example.Santa.repository;

import com.example.Santa.domain.DailyAdj;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyAdjRepository extends JpaRepository<DailyAdj, Long> {
}
