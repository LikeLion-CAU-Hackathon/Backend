package com.example.santa.controller;

import com.example.santa.domain.Answer;
import com.example.santa.dto.request.AnswerRequestDto;
import com.example.santa.service.AnswerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

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

    //로그인 한 유저가 답한 질문들 리스트
    @GetMapping("/list")
    public ResponseEntity<?> getMyAnsweredQuestionIds(Authentication authentication) {
        // 인증 정보 없을 때 처리
        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다.");
        }
        String memberEmail = authentication.getName();

        List<Long> questionIds = answerService.getAnsweredQuestionIdsByUser(memberEmail);
        return ResponseEntity.ok(questionIds);
    }

    // 해당 유저가 질문에 답했는지 안했는지 T/F
    @GetMapping("/list/{questionId}")
    public ResponseEntity<?> hasUserAnsweredQuestion(
            @PathVariable Long questionId,
            Authentication authentication) {

        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다.");
        }

        String email = authentication.getName();
        boolean answered = answerService.hasUserAnsweredQuestion(questionId, email);
        return ResponseEntity.ok(Map.of("answered", answered));
    }



}