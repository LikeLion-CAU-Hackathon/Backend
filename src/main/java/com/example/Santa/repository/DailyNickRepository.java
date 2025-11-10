package com.example.Santa.repository;

import com.example.Santa.domain.DailyNick;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyNickRepository extends JpaRepository<DailyNick, Long> {
    Optional<DailyNick> findByAppUserId(Long appUserId);
}
