package com.example.Santa.service;

import com.example.Santa.domain.Answer;
import com.example.Santa.domain.AppUser;
import com.example.Santa.domain.Thumbs;
import com.example.Santa.repository.AnswerRepository;
import com.example.Santa.repository.AppUserRepository;
import com.example.Santa.repository.ThumbsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ThumbsService {

    private final ThumbsRepository thumbsRepository;
    private final AnswerRepository answerRepository;
    private final AppUserRepository appUserRepository;

    private AppUser getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("로그인 유저를 찾을 수 없습니다."));
    }

    @Transactional
    public void like(Long answerId, Authentication auth) {
        AppUser user = getCurrentUser(auth);
        if (thumbsRepository.existsByAnswer_IdAndUser_Id(answerId, user.getId())) {
            return; // 이미 좋아요면 무시
        }
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("답변이 없습니다."));
        Thumbs thumbs = Thumbs.builder().answer(answer).user(user).build();
        thumbsRepository.save(thumbs);
    }

    @Transactional
    public void unlike(Long answerId, Authentication auth) {
        AppUser user = getCurrentUser(auth);
        thumbsRepository.deleteByAnswer_IdAndUser_Id(answerId, user.getId());
    }

    @Transactional(readOnly = true)
    public long count(Long answerId) {
        return thumbsRepository.countByAnswer_Id(answerId);
    }
}