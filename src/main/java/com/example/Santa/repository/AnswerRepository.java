package com.example.Santa.repository;

import com.example.Santa.domain.Answer;
import com.example.Santa.domain.AppUser;
import com.example.Santa.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    boolean existsByQuestionAndAppUser(Question question, AppUser user);

    @Query("SELECT a.question.id FROM Answer a WHERE a.appUser = :user")
    List<Long> findAnsweredQuestionIdsByAppUser(@Param("user") AppUser appUser);

    List<Answer> findByQuestionId(long questionId);
}
