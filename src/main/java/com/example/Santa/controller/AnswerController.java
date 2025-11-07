package com.example.Santa.controller;

import com.example.Santa.domain.Answer;
import com.example.Santa.dto.request.AnswerRequestDto;
import com.example.Santa.service.AnswerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            @AuthenticationPrincipal UserDetails userDetails) { // 1. 토큰에서 사용자 정보 가져오기

        String memberEmail = userDetails.getUsername();      // 2. UserDetails에서 username(이메일) 추출

        Answer createdAnswer = answerService.createAnswer(questionId, requestDto, memberEmail);

        // 4. 성공 응답 (201 Created)
        // 생성된 답변의 URI를 함께 반환 (표준 REST 응답)
        URI location = URI.create("/answers/{QuestionId}" + createdAnswer.getId());

        return ResponseEntity.created(location)
                .body("답변이 성공적으로 등록되었습니다. ID: " + createdAnswer.getId());
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