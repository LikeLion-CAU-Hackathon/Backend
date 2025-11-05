package com.example.Santa.controller;

import com.example.Santa.domain.Member;
import com.example.Santa.dto.request.JoinRequestDto;
import com.example.Santa.dto.response.TokenResponseDto;
import com.example.Santa.jwt.JwtTokenProvider;
import com.example.Santa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/join")
    public void join(@RequestBody JoinRequestDto joinRequestDto) {
        memberService.join(joinRequestDto);
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody JoinRequestDto joinRequestDto) {
        Member member = memberService.login(joinRequestDto);
        return TokenResponseDto.of(jwtTokenProvider.generateAccessToken(member.getName()), jwtTokenProvider.generateRefreshToken(member.getName()));
    }
}
