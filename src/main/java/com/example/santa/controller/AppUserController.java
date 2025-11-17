package com.example.santa.controller;

import com.example.santa.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/nickname/{questionId}")
    public ResponseEntity<Map<String, String>> getMyNickname(
            @PathVariable Long questionId,
            Authentication authentication
    ) {
        String nickname = appUserService.getMyNicknameByQuestion(questionId, authentication);
        return ResponseEntity.ok(Map.of("nickname", nickname));
    }
}