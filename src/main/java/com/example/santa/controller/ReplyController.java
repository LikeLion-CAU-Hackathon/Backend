package com.example.santa.controller;

import com.example.santa.domain.Reply;
import com.example.santa.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


@RestController
@RequiredArgsConstructor
@RequestMapping("/answers/{answerId}/reply")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<String> createReply(@PathVariable Long answerId,
                                              @RequestBody Map<String, String> body,
                                              Authentication auth) {
        String text = body.getOrDefault("text", "");
        replyService.createReply(answerId, text, auth);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("댓글 등록 성공");
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getReplyCount(@PathVariable Long answerId) {
        long count = replyService.countReply(answerId);
        return ResponseEntity.ok(count);
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getReplies(@PathVariable Long answerId) {
        List<Reply> replies = replyService.getReply(answerId);

        List<Map<String, Object>> result = replies.stream()
                .map(r -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("replyId", r.getId());
                    map.put("answerId", r.getAnswer().getId());
                    map.put("userId", r.getUser().getId());
                    map.put("userEmail", r.getUser().getEmail());
                    map.put("text", r.getText());
                    map.put("createdTime", r.getCreatedTime());
                    return map;
                })
                .toList();

        return ResponseEntity.ok(result);
        }
}