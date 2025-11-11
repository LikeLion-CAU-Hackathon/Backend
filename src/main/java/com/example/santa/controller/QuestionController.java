package com.example.santa.controller;


import com.example.santa.dto.response.AnswerResponseDto;
import com.example.santa.dto.response.QuestionResponseDto;
import com.example.santa.service.AnswerService;
import com.example.santa.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    // 날짜에 해당하는 질문 반환 api
    @GetMapping("/{date}") // url: /questions/2025-12-01
    public ResponseEntity<QuestionResponseDto> getQuestionByDate(
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        String content = questionService.getQuestionContentByDate(date);
        QuestionResponseDto responseDto = new QuestionResponseDto(content);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{questionId}/list")
    public ResponseEntity<List<AnswerResponseDto>> getAnswersByQuestion(@PathVariable Long questionId) {
        List<AnswerResponseDto> answers = answerService.getAnswersByQuestion(questionId);
        return ResponseEntity.ok(answers);
    }
}
