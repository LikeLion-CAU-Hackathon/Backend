package com.example.santa.controller;

import com.example.santa.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/nickname")
    public ResponseEntity<Map<String, String>> getMyNickname(Authentication authentication) {
        String nickname = appUserService.getMyNickname(authentication);
        return ResponseEntity.ok(Map.of("nickname", nickname));
    }
}
