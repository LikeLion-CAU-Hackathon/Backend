package com.example.Santa.service;

import com.example.Santa.domain.*;
import com.example.Santa.dto.request.AnswerRequestDto;
import com.example.Santa.repository.AnswerRepository;
import com.example.Santa.repository.MemberRepository;
import com.example.Santa.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, MemberRepository memberRepository) {
        this.answerRepository = answerRepository;
        this.memberRepository = memberRepository;
        this.questionRepository = questionRepository;
    }

    /**
     * 답변 생성 API
     * @param questionId 답변할 질문 ID
     * @param answerRequestDto 답변 내용 (contents)
     * @param memberEmail  JWT 토큰에서 가져온 로그인한 사용자의 이메일(ID)
     */
    public Answer createAnswer(Long questionId, AnswerRequestDto answerRequestDto, String memberEmail) {
        Question question = questionRepository.findById(questionId).orElseThrow(()-> new EntityNotFoundException("질문 찾을 수 없음"));
        Member member = memberRepository.findByEmail(memberEmail).orElseThrow(()-> new EntityNotFoundException("사용자 찾을 수 없음"));
        if(answerRepository.existsByQuestionAndMember(question, member)){
            throw new IllegalStateException("이미 이 질문에 답변함");
        }
        Answer newAnswer = new Answer(question, member, answerRequestDto.getContents());

        return answerRepository.save(newAnswer);
    }
}
