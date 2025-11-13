package com.example.santa.repository;

import com.example.santa.domain.Answer;
import com.example.santa.domain.AppUser;
import com.example.santa.domain.Question;
import com.example.santa.dto.response.AnswerListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    boolean existsByQuestionAndAppUser(Question question, AppUser user);

    @Query("SELECT a.question.id FROM Answer a WHERE a.appUser = :user")
    List<Long> findAnsweredQuestionIdsByAppUser(@Param("user") AppUser appUser);

    List<Answer> findByQuestionId(long questionId);

    List<Answer> findAllByQuestion_IdOrderByCreatedTimeDesc(Long questionId);
}
