package com.example.Santa.controller;

import com.example.Santa.service.ThumbsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answers/{answerId}/thumbs")
public class ThumbsController {

    private final ThumbsService thumbsService;

    // 좋아요
    @PostMapping
    public ResponseEntity<Void> like(@PathVariable Long answerId, Authentication auth) {
        thumbsService.like(answerId, auth);
        return ResponseEntity.ok().build();
    }

    // 좋아요 취소
    @DeleteMapping
    public ResponseEntity<Void> unlike(@PathVariable Long answerId, Authentication auth) {
        thumbsService.unlike(answerId, auth);
        return ResponseEntity.noContent().build();
    }

    // 좋아요 수
    @GetMapping("/count")
    public ResponseEntity<Long> count(@PathVariable Long answerId) {
        return ResponseEntity.ok(thumbsService.count(answerId));
    }
}