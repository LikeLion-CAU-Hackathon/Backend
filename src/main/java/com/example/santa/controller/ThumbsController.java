package com.example.santa.controller;

import com.example.santa.service.ThumbsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answers/{answerId}/thumbs")
public class ThumbsController {

    private final ThumbsService thumbsService;

    // 좋아요
    @PostMapping
    public ResponseEntity<Map<String, Object>> like(@PathVariable Long answerId, Authentication auth) {
        boolean liked = thumbsService.like(answerId, auth);
        long likeCount = thumbsService.count(answerId);
        return ResponseEntity.ok(
                Map.of(
                "liked",liked,
                "likeCount", likeCount
                )
        );
    }

    // 좋아요 취소
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> unlike(@PathVariable Long answerId, Authentication auth) {
        boolean liked = thumbsService.unlike(answerId, auth); // false 반환
        long likeCount = thumbsService.count(answerId);
        return ResponseEntity.ok(
                Map.of(
                        "liked", liked,
                        "likeCount", likeCount
                )
        );
    }

    // 좋아요 수
    @GetMapping("/count")
    public ResponseEntity<Long> count(@PathVariable Long answerId) {
        return ResponseEntity.ok(thumbsService.count(answerId));
    }
}