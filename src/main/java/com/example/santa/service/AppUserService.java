package com.example.santa.service;

import com.example.santa.domain.AppUser;
import com.example.santa.domain.Member;
import com.example.santa.repository.AppUserRepository;
import com.example.santa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public AppUser createUser(String email) {
        return appUserRepository.findByEmail(email)
                .orElseGet(() -> {
                    // 없으면 Member에서 이름 조회
                    Member member = memberRepository.findByEmail(email)
                            .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 Member가 존재하지 않습니다: " + email));

                    // Member의 이름을 그대로 AppUser로 저장
                    AppUser newUser = AppUser.builder()
                            .email(member.getEmail())
                            .name(member.getName())     // Member의 이름 그대로
                            .nickname(null)             // 닉네임은 나중에 설정
                            .build();

                    return appUserRepository.save(newUser);
                });
    }
}