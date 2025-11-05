package com.example.Santa.service;

import com.example.Santa.domain.Member;
import com.example.Santa.dto.request.JoinRequestDto;
import com.example.Santa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 비밀번호 인코더 DI(생성자 주입)
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(JoinRequestDto joinRequestDto) {
        // 해당 name이 이미 존재하는 경우
        if (memberRepository.existsByName(joinRequestDto.getName())) {
            return; // 나중에는 예외 처리
        }

        // 유저 객체 생성
        Member member = joinRequestDto.toEntity(bCryptPasswordEncoder);

        // 유저 정보 저장
        memberRepository.save(member);
    }

    public Member login(JoinRequestDto joinRequestDto) {
        Member member = memberRepository.findByName(joinRequestDto.getName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!bCryptPasswordEncoder.matches(joinRequestDto.getPassword(), member.getPassword())) {
            return null;
        }

        return member;
    }
}
