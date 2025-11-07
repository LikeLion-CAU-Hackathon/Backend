package com.example.Santa.repository;

import com.example.Santa.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByAccessTimeBetween(LocalDateTime startofDay, LocalDateTime endofDay);
}
