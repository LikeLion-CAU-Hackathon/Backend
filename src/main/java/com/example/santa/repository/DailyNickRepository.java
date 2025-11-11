package com.example.santa.repository;

import com.example.santa.domain.DailyNick;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyNickRepository extends JpaRepository<DailyNick, Long> {
    Optional<DailyNick> findByAppUserId(Long appUserId);
}
