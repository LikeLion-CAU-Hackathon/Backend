package com.example.santa.repository;

import com.example.santa.domain.DailyNoun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyNounRepository extends JpaRepository<DailyNoun, Long> {
}
