package com.example.Santa.repository;

import com.example.Santa.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByAccessDay(LocalDate accessDay);
}
