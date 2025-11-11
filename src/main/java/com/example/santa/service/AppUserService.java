package com.example.santa.service;

import com.example.santa.domain.*;
import com.example.santa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final MemberRepository memberRepository;
    private final DailyNounRepository dailyNounRepository;
    private final DailyNickRepository dailyNickRepository;
    private final DailyAdjRepository dailyAdjRepository;

    private final Random random = new Random();

    @Transactional
    public AppUser createUser(String email) {
        return appUserRepository.findByEmail(email)
                .orElseGet(() -> {
                    Member member = memberRepository.findByEmail(email)
                            .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 Member가 존재하지 않습니다: " + email));

                    // 유저 등록 시 랜덤 닉네임 바로 발급 (기존 유저 매일 변동 -> DailyNickService 에서
                    List<DailyAdj> dailyAdjs = dailyAdjRepository.findAll();
                    List<DailyNoun> dailyNouns = dailyNounRepository.findAll();

                    if (dailyAdjs.isEmpty() || dailyNouns.isEmpty()) {
                        throw new IllegalArgumentException("no data");
                    }

                    DailyAdj dailyAdj = dailyAdjs.get(random.nextInt(dailyAdjs.size()));
                    DailyNoun dailyNoun = dailyNouns.get(random.nextInt(dailyNouns.size()));
                    String newNickname = dailyAdj.getAdjective()+" "+dailyNoun.getNoun();

                    AppUser newUser = AppUser.builder()
                            .email(member.getEmail())
                            .name(member.getName())     // Member의 이름 그대로
                            .nickname(newNickname)
                            .build();

                    AppUser savedUser = appUserRepository.save(newUser);

                    DailyNick dailyNick = new DailyNick();
                    dailyNick.setAppUser(savedUser); // ★ 생성된 AppUser와 연결
                    dailyNick.setDailyAdj(dailyAdj);
                    dailyNick.setDailyNoun(dailyNoun);

                    dailyNickRepository.save(dailyNick);

                    return savedUser;
                });
    }
}