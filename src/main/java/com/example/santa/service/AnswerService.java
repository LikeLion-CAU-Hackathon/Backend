package com.example.santa.service;

import com.example.santa.domain.*;
import com.example.santa.dto.request.AnswerRequestDto;
import com.example.santa.dto.response.AnswerListDto;
import com.example.santa.dto.response.AnswerResponseDto;
import com.example.santa.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AppUserRepository appUserRepository;
    private final QuestionRepository questionRepository;
    private final ThumbsRepository thumbsRepository;
    private final ReplyRepository replyRepository;

    /**
     * 답변 생성 API
     * @param questionId 답변할 질문 ID
     * @param answerRequestDto 답변 내용 (contents)
     * @param email  JWT 토큰에서 가져온 로그인한 사용자의 이메일(ID)
     */
    public Answer createAnswer(Long questionId, AnswerRequestDto answerRequestDto, String email) {
        Question question = questionRepository.findById(questionId).orElseThrow(()-> new EntityNotFoundException("질문 찾을 수 없음"));
        AppUser appUser = appUserRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("사용자 찾을 수 없음"));
        if(answerRepository.existsByQuestionAndAppUser(question, appUser)){
            throw new IllegalStateException("이미 이 질문에 답변함");
        }
        Answer newAnswer = new Answer(question, appUser, answerRequestDto.getContents());

        return answerRepository.save(newAnswer);
    }

    @Transactional(readOnly = true) // 읽기 전용 트랜잭션 (성능 향상)
    public List<Long> getAnsweredQuestionIdsByUser(String email) {

        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. (email: " + email + ")"));

        return answerRepository.findAnsweredQuestionIdsByAppUser(appUser);
    }

    @Transactional(readOnly = true)
    public boolean hasUserAnsweredQuestion(Long questionId, String email) {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. (email: " + email + ")"));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("질문을 찾을 수 없습니다. (id: " + questionId + ")"));

        return answerRepository.existsByQuestionAndAppUser(question, appUser);
    }

    public List<AnswerResponseDto> getAnswersByQuestion(Long questionId) {
        List<Answer> answers = answerRepository.findByQuestionId(questionId);

        return answers.stream()
                .map(AnswerResponseDto::fromEntity)
                .toList();
    }

    public List<AnswerListDto> getAnswersWithCounts(Long questionId) {
        // LocalDate today = LocalDate.now();
        LocalDate today = LocalDate.now();
        boolean isChristmas = today.equals(LocalDate.of(2025, 12, 25));

        return answerRepository.findAllByQuestion_IdOrderByCreatedTimeDesc(questionId)
                .stream()
                .map(a -> AnswerListDto.builder()
                        .answerId(a.getId())
                        .userName(a.getAppUser().getName())
                        .userNickname(isChristmas ? a.getAppUser().getName() :a.getAppUser().getNickname())
                        .contents(a.getContents())
                        .createdTime(a.getCreatedTime())
                        .likeCount(thumbsRepository.countByAnswer_Id(a.getId()))
                        .replyCount(replyRepository.countByAnswer_Id(a.getId()))
                        .build()
                )
                .toList();
    }

}
