package com.example.santa.repository;

import com.example.santa.domain.AppUser;
import com.example.santa.domain.Question;
import com.example.santa.domain.QuestionNick;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionNickRepository extends JpaRepository<QuestionNick, Long> {
    Optional<QuestionNick> findByQuestion_IdAndAppUser_Id(Long questionId, Long userId);
}
