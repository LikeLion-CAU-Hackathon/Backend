package com.example.Santa.repository;

import com.example.Santa.domain.Answer;
import com.example.Santa.domain.Member;
import com.example.Santa.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    boolean existsByQuestionAndMember(Question question, Member member);
}
