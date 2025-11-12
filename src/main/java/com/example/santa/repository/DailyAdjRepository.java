package com.example.santa.repository;

import com.example.santa.domain.DailyAdj;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyAdjRepository extends JpaRepository<DailyAdj, Long> {
}
