package com.example.Santa.repository;

import com.example.Santa.domain.DailyNoun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyNounRepository extends JpaRepository<DailyNoun, Long> {
}
