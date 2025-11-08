package com.example.Santa.controller;

import com.example.Santa.domain.Answer;
import com.example.Santa.dto.request.AnswerRequestDto;
import com.example.Santa.service.AnswerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/{questionId}")
    public ResponseEntity<String> createAnswer(
            @PathVariable Long questionId,
            @RequestBody AnswerRequestDto requestDto,
            Authentication authentication) {

        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다.");
        }

        String email = authentication.getName();

        Answer createdAnswer = answerService.createAnswer(questionId, requestDto, email);

        URI location = URI.create("/answers/" + createdAnswer.getId());
        return ResponseEntity.created(location)
                .body("답변 등록 성공! ID: " + createdAnswer.getId());
    }


    // 404 Not Found (질문이나 유저가 없을 때)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    // 409 Conflict (중복 답변일 때)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleConflict(IllegalStateException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
}