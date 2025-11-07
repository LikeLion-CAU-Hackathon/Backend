package com.example.Santa.controller;


import com.example.Santa.dto.response.QuestionResponseDto;
import com.example.Santa.service.QuestionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{date}") // url: /questions/2025-12-01
    public ResponseEntity<QuestionResponseDto> getQuestionByDate(
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        String content = questionService.getQuestionContentByDate(date);
        QuestionResponseDto responseDto = new QuestionResponseDto(content);

        return ResponseEntity.ok(responseDto);
    }

}
