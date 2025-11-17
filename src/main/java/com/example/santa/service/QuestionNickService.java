package com.example.santa.service;

import com.example.santa.domain.*;
import com.example.santa.repository.DailyAdjRepository;
import com.example.santa.repository.DailyNounRepository;
import com.example.santa.repository.QuestionNickRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuestionNickService {
    private final QuestionNickRepository questionNickRepository;
    private final DailyAdjRepository dailyAdjRepository;
    private final DailyNounRepository dailyNounRepository;

    private final Random random = new Random();

    @Transactional
    public QuestionNick getOrCreate(Question question, AppUser appUser) {

        return questionNickRepository
                .findByQuestion_IdAndAppUser_Id(question.getId(), appUser.getId())
                .orElseGet(() -> {

                    List<DailyAdj> adjectives = dailyAdjRepository.findAll();
                    List<DailyNoun> nouns = dailyNounRepository.findAll();

                    if (adjectives.isEmpty() || nouns.isEmpty()) {
                        throw new IllegalStateException("닉네임 데이터(daily adj/noun)가 없습니다.");
                    }

                    DailyAdj adj = adjectives.get(random.nextInt(adjectives.size()));
                    DailyNoun noun = nouns.get(random.nextInt(nouns.size()));
                    String nickname = adj.getAdjective() + " " + noun.getNoun();

                    QuestionNick qn = QuestionNick.builder()
                            .appUser(appUser)
                            .question(question)
                            .dailyAdj(adj)
                            .dailyNoun(noun)
                            .nickname(nickname)
                            .build();

                    return questionNickRepository.save(qn);
                });
    }


    @Transactional
    public String getNickname(Long questionId, Long userId) {
        return questionNickRepository
                .findByQuestion_IdAndAppUser_Id(questionId, userId)
                .map(QuestionNick::getNickname)
                .orElse(null);
    }
}

