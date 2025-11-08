package com.example.Santa.repository;

import com.example.Santa.domain.Answer;
import com.example.Santa.domain.AppUser;
import com.example.Santa.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    boolean existsByQuestionAndAppUser(Question question, AppUser user);
}
