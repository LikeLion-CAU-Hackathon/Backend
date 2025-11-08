package com.example.Santa.service;

import com.example.Santa.domain.Question;
import com.example.Santa.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly=true)
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public String getQuestionContentByDate(LocalDate date){

        Question question = questionRepository
                .findByAccessDay(date)
                .orElseThrow(() -> new EntityNotFoundException("해당 날짜 질문 찾을 수 없음: " +date));

        return question.getContent();
    }
}
