package com.example.santa.service;

import com.example.santa.domain.Answer;
import com.example.santa.domain.AppUser;
import com.example.santa.domain.Reply;
import com.example.santa.dto.response.ReplyResponseDto;
import com.example.santa.repository.AnswerRepository;
import com.example.santa.repository.AppUserRepository;
import com.example.santa.repository.ReplyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final AnswerRepository answerRepository;
    private final AppUserRepository appUserRepository;

    @Transactional
    public Reply createReply(Long answerId, String text, Authentication authentication) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("댓글 내용을 입력하세요.");
        }
        if (authentication == null || authentication.getName() == null) {
            throw new IllegalStateException("인증 정보가 없습니다.");
        }

        String email = authentication.getName();

        AppUser user = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 답변입니다."));

        Reply reply = Reply.builder()
                .answer(answer)
                .user(user)
                .text(text.trim())
                .createdTime(LocalDateTime.now())
                .build();

        return replyRepository.save(reply);
    }

    public long countReply(Long answerId) {
        return replyRepository.countByAnswer_Id(answerId);
    }

    public List<ReplyResponseDto> getReply(Long answerId) {
        LocalDate today = LocalDate.now();
        boolean isChristmas = today.equals(LocalDate.of(2025, 12, 25));

        return replyRepository.findAllByAnswer_IdOrderByCreatedTimeAsc(answerId)
                .stream()
                .map(r->ReplyResponseDto.builder()
                        .replyId(r.getId())
                        .answerId(r.getAnswer().getId())
                        .userId(r.getUser().getId())
                        .userName(r.getUser().getName())
                        .userNickname(isChristmas ? r.getUser().getName():r.getUser().getNickname())
                        .text(r.getText())
                        .createdTime(r.getCreatedTime())
                        .build())
                .toList();
    }
}