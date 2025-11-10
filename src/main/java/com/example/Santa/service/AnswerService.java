package com.example.Santa.service;

import com.example.Santa.domain.*;
import com.example.Santa.dto.request.AnswerRequestDto;
import com.example.Santa.dto.response.AnswerResponseDto;
import com.example.Santa.repository.AnswerRepository;
import com.example.Santa.repository.AppUserRepository;
import com.example.Santa.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AppUserRepository appUserRepository;
    private final QuestionRepository questionRepository;

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

}
