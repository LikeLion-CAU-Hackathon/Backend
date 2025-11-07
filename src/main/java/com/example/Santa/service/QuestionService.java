package com.example.Santa.service;

import com.example.Santa.domain.Question;
import com.example.Santa.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@Transactional(readOnly=true)
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public String getQuestionContentByDate(LocalDate date){
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        Question question = questionRepository
                .findByAccessTimeBetween(startOfDay, endOfDay)
                .orElseThrow(() -> new EntityNotFoundException("해당 날짜 질문 찾을 수 없음: " +date));

        return question.getContent();
    }
}
